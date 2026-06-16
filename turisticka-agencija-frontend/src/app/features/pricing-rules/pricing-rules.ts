import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  Arrangement,
  ArrangementService,
  PricingRule,
} from '../../core/services/arrangement';
import { SidebarMenu } from '../../layout/sidebar-menu/sidebar-menu';

@Component({
  selector: 'app-pricing-rules',
  standalone: true,
  imports: [CommonModule, FormsModule, SidebarMenu],
  templateUrl: './pricing-rules.html',
  styleUrl: './pricing-rules.css',
})
export class PricingRules implements OnInit {
  rules: PricingRule[] = [];
  arrangements: Arrangement[] = [];

  errorMessage = '';
  successMessage = '';

  editingRuleId: number | null = null;

  form: PricingRule = this.getEmptyForm();

  constructor(private arrangementService: ArrangementService) {}

  ngOnInit(): void {
    this.loadRules();
    this.loadArrangements();
  }

  getEmptyForm(): PricingRule {
    return {
      name: '',
      type: 'SEASON',
      percentage: 0,
      arrangementId: null,
      seasonStart: null,
      seasonEnd: null,
      minOccupancyPercent: null,
      maxDaysBeforeStart: null,
      minDaysBeforeStart: null,
      active: true,
    };
  }

  loadRules(): void {
    this.arrangementService.getPricingRules().subscribe({
      next: (data) => {
        this.rules = data;
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Greška pri učitavanju pravila cena.';
      },
    });
  }

  loadArrangements(): void {
    this.arrangementService.getAll().subscribe({
      next: (data) => {
        this.arrangements = data;
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  onTypeChange(): void {
    this.form.arrangementId = null;
    this.form.seasonStart = null;
    this.form.seasonEnd = null;
    this.form.minOccupancyPercent = null;
    this.form.maxDaysBeforeStart = null;
    this.form.minDaysBeforeStart = null;
  }

  saveRule(): void {
    this.errorMessage = '';
    this.successMessage = '';

    const request = this.prepareRequest();

    if (this.editingRuleId) {
      this.arrangementService.updatePricingRule(this.editingRuleId, request).subscribe({
        next: () => {
          this.successMessage = 'Pravilo je uspešno izmenjeno.';
          this.resetForm();
          this.loadRules();
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Greška pri izmeni pravila.';
        },
      });

      return;
    }

    this.arrangementService.createPricingRule(request).subscribe({
      next: () => {
        this.successMessage = 'Pravilo je uspešno kreirano.';
        this.resetForm();
        this.loadRules();
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Greška pri kreiranju pravila.';
      },
    });
  }

  prepareRequest(): PricingRule {
    const request: PricingRule = {
      name: this.form.name,
      type: this.form.type,
      percentage: this.form.percentage,
      active: this.form.active,

      arrangementId: null,
      seasonStart: null,
      seasonEnd: null,
      minOccupancyPercent: null,
      maxDaysBeforeStart: null,
      minDaysBeforeStart: null,
    };

    if (this.form.type === 'SEASON') {
      request.arrangementId = this.form.arrangementId;
      request.seasonStart = this.form.seasonStart;
      request.seasonEnd = this.form.seasonEnd;
    }

    if (this.form.type === 'OCCUPANCY') {
      request.minOccupancyPercent = this.form.minOccupancyPercent;
    }

    if (this.form.type === 'LAST_MINUTE') {
      request.maxDaysBeforeStart = this.form.maxDaysBeforeStart;
    }

    if (this.form.type === 'EARLY_BOOKING') {
      request.minDaysBeforeStart = this.form.minDaysBeforeStart;
    }

    return request;
  }

  editRule(rule: PricingRule): void {
    this.editingRuleId = rule.id ?? null;

    this.form = {
      name: rule.name,
      type: rule.type,
      percentage: rule.percentage,

      arrangementId: rule.arrangement?.id ?? null,

      seasonStart: rule.seasonStart ?? null,
      seasonEnd: rule.seasonEnd ?? null,

      minOccupancyPercent: rule.minOccupancyPercent ?? null,

      maxDaysBeforeStart: rule.maxDaysBeforeStart ?? null,
      minDaysBeforeStart: rule.minDaysBeforeStart ?? null,

      active: rule.active,
    };
  }

  deleteRule(id: number | undefined): void {
    if (!id) {
      return;
    }

    this.arrangementService.deletePricingRule(id).subscribe({
      next: () => {
        this.successMessage = 'Pravilo je obrisano.';
        this.loadRules();
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Greška pri brisanju pravila.';
      },
    });
  }

  resetForm(): void {
    this.editingRuleId = null;
    this.form = this.getEmptyForm();
  }

  getRuleTypeLabel(type: string): string {
    if (type === 'SEASON') return 'Sezona';
    if (type === 'OCCUPANCY') return 'Popunjenost';
    if (type === 'LAST_MINUTE') return 'Last minute';
    if (type === 'EARLY_BOOKING') return 'Early booking';

    return type;
  }
}