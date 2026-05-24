import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';

import { ArrangementActivity, ArrangementService } from '../../../core/services/arrangement';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';

@Component({
  selector: 'app-additional-activities-customer',
  standalone: true,
  imports: [CommonModule, RouterLink, SidebarMenu],
  templateUrl: './additional-activities-customer.html',
  styleUrl: './additional-activities-customer.css',
})
export class AdditionalActivitiesCustomer implements OnInit {
  arrangementTermId!: number;

  activities: ArrangementActivity[] = [];

  loading = false;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private arrangementService: ArrangementService,
  ) {}

  ngOnInit(): void {
    window.scrollTo({
      top: 0,
      behavior: 'instant' as ScrollBehavior,
    });

    this.arrangementTermId = Number(this.route.snapshot.queryParamMap.get('arrangementTermId'));

    if (!this.arrangementTermId) {
      this.errorMessage = 'Termin aranžmana nije pronađen.';
      return;
    }

    this.loadActivities();
  }

  loadActivities(): void {
    this.loading = true;
    this.errorMessage = '';

    this.arrangementService.getActivitiesForArrangementTerm(this.arrangementTermId).subscribe({
      next: (data) => {
        this.activities = data;
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'Greška pri učitavanju dodatnih aktivnosti.';
        this.loading = false;
      },
    });
  }

  getImageUrl(imageUrl: string): string {
    if (!imageUrl) {
      return '/assets/plaza.jpg';
    }

    if (imageUrl.startsWith('http')) {
      return imageUrl;
    }

    return `http://localhost:8080${imageUrl}`;
  }
}
