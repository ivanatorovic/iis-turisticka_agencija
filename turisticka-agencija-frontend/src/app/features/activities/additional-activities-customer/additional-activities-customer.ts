import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

import {
  AdditionalActivityRegistrationResponse,
  ArrangementActivity,
  ArrangementService,
} from '../../../core/services/arrangement';

import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';

@Component({
  selector: 'app-additional-activities-customer',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule, SidebarMenu],
  templateUrl: './additional-activities-customer.html',
  styleUrl: './additional-activities-customer.css',
})
export class AdditionalActivitiesCustomer implements OnInit {
  arrangementTermId!: number;

  activities: ArrangementActivity[] = [];
  myRegistrations: AdditionalActivityRegistrationResponse[] = [];

  selectedExecutionId: number | null = null;
  numberOfParticipants: number | null = null;

  registrationToCancelId: number | null = null;

  loading = false;

  errorMessage = '';
  successMessage = '';

  constructor(
    private route: ActivatedRoute,
    private arrangementService: ArrangementService,
  ) {}

  ngOnInit(): void {
    this.arrangementTermId = Number(this.route.snapshot.queryParamMap.get('arrangementTermId'));

    if (!this.arrangementTermId) {
      this.errorMessage = 'Termin aranžmana nije pronađen.';
      return;
    }

    this.loadPageData();
  }

  loadPageData(): void {
    this.loading = true;

    this.errorMessage = '';
    this.successMessage = '';

    this.arrangementService.getActivitiesForArrangementTerm(this.arrangementTermId).subscribe({
      next: (activities) => {
        this.activities = activities;
        this.loadMyRegistrations();
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'Greška pri učitavanju aktivnosti.';
        this.loading = false;
      },
    });
  }

  loadMyRegistrations(): void {
    this.arrangementService.getMyActivityRegistrations().subscribe({
      next: (registrations) => {
        this.myRegistrations = registrations.filter(
          (registration) =>
            registration.status === 'ACTIVE' &&
            this.activities.some((activity) => activity.id === registration.executionId),
        );

        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'Greška pri učitavanju prijava.';
        this.loading = false;
      },
    });
  }

  getAvailableActivities(): ArrangementActivity[] {
    return this.activities.filter(
      (activity) =>
        !this.myRegistrations.some((registration) => registration.executionId === activity.id),
    );
  }

  openRegisterForm(executionId: number): void {
    this.selectedExecutionId = executionId;

    this.numberOfParticipants = null;

    this.errorMessage = '';
    this.successMessage = '';
  }

  closeRegisterForm(): void {
    this.selectedExecutionId = null;
    this.numberOfParticipants = null;
  }

  confirmRegistration(activity: ArrangementActivity): void {
    this.errorMessage = '';
    this.successMessage = '';

    this.arrangementService
      .registerForActivity(activity.id, {
        numberOfParticipants: this.numberOfParticipants,
      })
      .subscribe({
        next: () => {
          this.successMessage = 'Uspešno ste prijavljeni na dodatnu aktivnost.';

          this.selectedExecutionId = null;
          this.numberOfParticipants = null;

          this.loadPageData();
        },
        error: (error) => {
          this.errorMessage = error.error?.message || 'Prijava na dodatnu aktivnost nije uspela.';
        },
      });
  }

  openCancelModal(registrationId: number): void {
    this.registrationToCancelId = registrationId;
  }

  closeCancelModal(): void {
    this.registrationToCancelId = null;
  }

  confirmCancelRegistration(): void {
    if (!this.registrationToCancelId) {
      return;
    }

    this.errorMessage = '';
    this.successMessage = '';

    this.arrangementService.cancelActivityRegistration(this.registrationToCancelId).subscribe({
      next: () => {
        this.successMessage = 'Prijava na dodatnu aktivnost je uspešno otkazana.';

        this.registrationToCancelId = null;

        this.loadPageData();
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'Otkazivanje prijave nije uspelo.';

        this.registrationToCancelId = null;
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
