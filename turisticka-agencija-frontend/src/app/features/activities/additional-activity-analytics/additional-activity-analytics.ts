import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import {
  Arrangement,
  ArrangementTerm,
  ArrangementService,
} from '../../../core/services/arrangement';

import {
  AdditionalActivityAnalyticsResponse,
  AdditionalActivityService,
} from '../../../core/services/additional.activity';

import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';

@Component({
  selector: 'app-additional-activity-analytics',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, SidebarMenu],
  templateUrl: './additional-activity-analytics.html',
  styleUrl: './additional-activity-analytics.css',
})
export class AdditionalActivityAnalytics implements OnInit {
  arrangements: Arrangement[] = [];
  selectedTerms: ArrangementTerm[] = [];

  analytics: AdditionalActivityAnalyticsResponse | null = null;

  filters = {
    arrangementId: null as number | null,
    arrangementTermId: null as number | null,
  };

  loading = false;
  loadingArrangements = false;
  errorMessage = '';
  generatingPdf = false;

  constructor(
    private arrangementService: ArrangementService,
    private additionalActivityService: AdditionalActivityService,
  ) {}

  ngOnInit(): void {
    this.loadArrangements();
  }

  loadArrangements(): void {
    this.loadingArrangements = true;
    this.errorMessage = '';

    this.arrangementService.getAll().subscribe({
      next: (arrangements) => {
        this.arrangements = arrangements;
        this.loadingArrangements = false;

        if (this.arrangements.length > 0) {
          this.filters.arrangementId = this.arrangements[0].id;
          this.onArrangementChange();
          this.loadAnalytics();
        }
      },
      error: (error) => {
        this.loadingArrangements = false;
        this.errorMessage = error.error?.message || 'Greška pri učitavanju aranžmana.';
      },
    });
  }

  onArrangementChange(): void {
    const selectedArrangement = this.arrangements.find(
      (arrangement) => arrangement.id === Number(this.filters.arrangementId),
    );

    this.selectedTerms = selectedArrangement?.arrangementTerms || [];
    this.filters.arrangementTermId = null;
  }

  loadAnalytics(): void {
    if (!this.filters.arrangementId) {
      this.errorMessage = 'Morate izabrati aranžman.';
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.analytics = null;

    this.additionalActivityService
      .getAdditionalActivityAnalytics({
        arrangementId: Number(this.filters.arrangementId),
        arrangementTermId: this.filters.arrangementTermId
          ? Number(this.filters.arrangementTermId)
          : null,
      })
      .subscribe({
        next: (analytics) => {
          this.analytics = analytics;
          this.loading = false;
        },
        error: (error) => {
          this.errorMessage = error.error?.message || 'Greška pri učitavanju izveštaja.';
          this.loading = false;
        },
      });
  }

  clearFilters(): void {
    this.filters.arrangementTermId = null;

    this.loadAnalytics();
  }

  getBarWidth(value: number, max: number): number {
    if (!max || max <= 0) {
      return 0;
    }

    return Math.max((value / max) * 100, 4);
  }

  getMaxParticipants(): number {
    return Math.max(...(this.analytics?.popularity.map((item) => item.participantsCount) || [0]));
  }

  getMaxRevenue(): number {
    return Math.max(...(this.analytics?.revenue.map((item) => item.revenue) || [0]));
  }

  generatePdfReport(): void {
    if (!this.filters.arrangementId) {
      this.errorMessage = 'Morate izabrati aranžman.';
      return;
    }

    this.generatingPdf = true;
    this.errorMessage = '';

    this.additionalActivityService
      .generateAnalyticsPdf(
        Number(this.filters.arrangementId),
        this.filters.arrangementTermId ? Number(this.filters.arrangementTermId) : null,
      )
      .subscribe({
        next: (blob) => {
          const fileUrl = window.URL.createObjectURL(blob);
          const link = document.createElement('a');

          link.href = fileUrl;
          link.download = 'izvestaj-dodatne-aktivnosti.pdf';
          link.click();

          window.URL.revokeObjectURL(fileUrl);
          this.generatingPdf = false;
        },
        error: (error) => {
          this.errorMessage = error.error?.message || 'Generisanje PDF izveštaja nije uspelo.';
          this.generatingPdf = false;
        },
      });
  }
}
