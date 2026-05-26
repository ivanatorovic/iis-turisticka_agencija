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

  allTermActivities: ArrangementActivity[] = [];
  myRegistrations: AdditionalActivityRegistrationResponse[] = [];

  registrationToCancelId: number | null = null;

  registrationToUpdateId: number | null = null;
  updatedNumberOfParticipants: number | null = null;

  loading = false;

  errorMessage = '';
  successMessage = '';
  updateRegistrationErrorMessage = '';

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
        this.allTermActivities = activities;
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
            this.allTermActivities.some((activity) => activity.id === registration.executionId),
        );

        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'Greška pri učitavanju prijava.';
        this.loading = false;
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

  openUpdateForm(registration: AdditionalActivityRegistrationResponse): void {
    this.registrationToUpdateId = registration.id;
    this.updatedNumberOfParticipants = registration.numberOfParticipants;

    this.errorMessage = '';
    this.successMessage = '';
    this.updateRegistrationErrorMessage = '';
  }

  closeUpdateForm(): void {
    this.registrationToUpdateId = null;
    this.updatedNumberOfParticipants = null;
    this.updateRegistrationErrorMessage = '';
  }

  confirmUpdateRegistration(): void {
    if (!this.registrationToUpdateId) {
      return;
    }

    this.errorMessage = '';
    this.successMessage = '';
    this.updateRegistrationErrorMessage = '';

    if (!this.updatedNumberOfParticipants || this.updatedNumberOfParticipants <= 0) {
      this.updateRegistrationErrorMessage = 'Broj osoba mora biti veći od 0.';
      return;
    }

    this.arrangementService
      .updateActivityRegistration(this.registrationToUpdateId, {
        numberOfParticipants: this.updatedNumberOfParticipants,
      })
      .subscribe({
        next: () => {
          this.successMessage = 'Prijava je uspešno ažurirana.';

          this.registrationToUpdateId = null;
          this.updatedNumberOfParticipants = null;
          this.updateRegistrationErrorMessage = '';

          this.loadPageData();
        },
        error: (error) => {
          this.updateRegistrationErrorMessage =
            error.error?.message || 'Ažuriranje prijave nije uspelo.';
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
