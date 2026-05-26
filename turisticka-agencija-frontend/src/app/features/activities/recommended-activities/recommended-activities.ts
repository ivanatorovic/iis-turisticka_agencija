import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

import {
  AdditionalActivityExecutionFilter,
  AdditionalActivityRegistrationResponse,
  ArrangementActivity,
  ArrangementService,
} from '../../../core/services/arrangement';

import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';

@Component({
  selector: 'app-recommended-activities',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule, SidebarMenu],
  templateUrl: './recommended-activities.html',
  styleUrl: './recommended-activities.css',
})
export class RecommendedActivities implements OnInit {
  arrangementTermId!: number;
  filtersOpen = false;

  activities: ArrangementActivity[] = [];
  allTermActivities: ArrangementActivity[] = [];
  myRegistrations: AdditionalActivityRegistrationResponse[] = [];

  selectedExecutionId: number | null = null;
  numberOfParticipants: number | null = null;

  loading = false;

  errorMessage = '';
  successMessage = '';
  registrationErrorMessage = '';

  filters: AdditionalActivityExecutionFilter = {
    dateFrom: '',
    dateTo: '',
    minPrice: null,
    maxPrice: null,
    minDuration: null,
    maxDuration: null,
    minAvailableSpots: null,
    onlyAvailable: true,
  };

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

  toggleFilters(): void {
    this.filtersOpen = !this.filtersOpen;
  }

  loadPageData(): void {
    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.arrangementService.getActivitiesForArrangementTerm(this.arrangementTermId).subscribe({
      next: (allActivities) => {
        this.allTermActivities = allActivities;
        this.loadFilteredActivities();
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'Greška pri učitavanju aktivnosti.';
        this.loading = false;
      },
    });
  }

  loadFilteredActivities(): void {
    this.arrangementService
      .getFilteredActivitiesForArrangementTerm(this.arrangementTermId, this.filters)
      .subscribe({
        next: (activities) => {
          this.activities = activities;
          this.loadMyRegistrations();
        },
        error: (error) => {
          this.errorMessage = error.error?.message || 'Greška pri filtriranju aktivnosti.';
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

  applyFilters(): void {
    const currentScroll = window.scrollY;

    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';
    this.closeRegisterForm();

    this.loadFilteredActivities();

    setTimeout(() => {
      window.scrollTo({
        top: currentScroll,
        behavior: 'instant' as ScrollBehavior,
      });
    });
  }

  resetFilters(): void {
    this.filters = {
      dateFrom: '',
      dateTo: '',
      minPrice: null,
      maxPrice: null,
      minDuration: null,
      maxDuration: null,
      minAvailableSpots: null,
      onlyAvailable: true,
    };

    this.applyFilters();
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
    this.registrationErrorMessage = '';
  }

  closeRegisterForm(): void {
    this.selectedExecutionId = null;
    this.numberOfParticipants = null;
    this.registrationErrorMessage = '';
  }

  confirmRegistration(activity: ArrangementActivity): void {
    this.errorMessage = '';
    this.successMessage = '';
    this.registrationErrorMessage = '';

    if (!this.numberOfParticipants || this.numberOfParticipants <= 0) {
      this.registrationErrorMessage = 'Morate uneti broj prijavljenih osoba.';
      return;
    }

    if (this.numberOfParticipants > activity.availableSpots) {
      this.registrationErrorMessage = 'Broj osoba ne može biti veći od broja slobodnih mesta.';
      return;
    }

    this.arrangementService
      .registerForActivity(activity.id, {
        numberOfParticipants: this.numberOfParticipants,
      })
      .subscribe({
        next: () => {
          this.successMessage = 'Uspešno ste prijavljeni na dodatnu aktivnost.';

          this.selectedExecutionId = null;
          this.numberOfParticipants = null;
          this.registrationErrorMessage = '';

          this.loadPageData();
        },
        error: (error) => {
          this.registrationErrorMessage =
            error.error?.message || 'Prijava na dodatnu aktivnost nije uspela.';
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
