import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { ArrangementActivity, ArrangementService } from '../../../../core/services/arrangement';

import { SidebarMenu } from '../../../../layout/sidebar-menu/sidebar-menu';

import {
  AdditionalActivityService,
  AdditionalActivityShortResponse,
} from '../../../../core/services/additional.activity';

@Component({
  selector: 'app-manager-arrangements-activities',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule, SidebarMenu],
  templateUrl: './manager-arrangements-activities.html',
  styleUrl: './manager-arrangements-activities.css',
})
export class ManagerArrangementsActivities implements OnInit {
  arrangementTermId!: number;

  activities: ArrangementActivity[] = [];
  allActivities: AdditionalActivityShortResponse[] = [];

  selectedActivity: AdditionalActivityShortResponse | null = null;

  searchText = '';
  modalStep: 1 | 2 = 1;
  showAddModal = false;

  executionForm = {
    activityDate: '',
    startTime: '',
    durationMinutes: null as number | null,
    capacity: null as number | null,
    price: null as number | null,
  };

  loading = false;
  modalLoading = false;

  errorMessage = '';
  successMessage = '';
  modalErrorMessage = '';

  termStartDate = '';
  termEndDate = '';

  constructor(
    private route: ActivatedRoute,
    private arrangementService: ArrangementService,
    private additionalActivityService: AdditionalActivityService,
  ) {}

  ngOnInit(): void {
    window.scrollTo({
      top: 0,
      behavior: 'instant' as ScrollBehavior,
    });
    this.arrangementTermId = Number(this.route.snapshot.paramMap.get('id'));

    this.termStartDate = this.route.snapshot.queryParamMap.get('startDate') || '';
    this.termEndDate = this.route.snapshot.queryParamMap.get('endDate') || '';

    this.loadActivities();
  }

  loadActivities(): void {
    this.loading = true;
    this.errorMessage = '';

    this.arrangementService.getActivitiesForArrangementTerm(this.arrangementTermId).subscribe({
      next: (data: ArrangementActivity[]) => {
        this.activities = data;
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage =
          error.error?.message || 'Greška pri učitavanju dodatnih aktivnosti za termin aranžmana.';
        this.loading = false;
      },
    });
  }

  openAddModal(): void {
    this.showAddModal = true;
    this.modalStep = 1;
    this.modalErrorMessage = '';
    this.successMessage = '';
    this.selectedActivity = null;
    this.searchText = '';
    this.resetForm();

    if (this.allActivities.length === 0) {
      this.loadShortActivities();
    }
  }

  closeAddModal(): void {
    this.showAddModal = false;
    this.modalStep = 1;
    this.modalErrorMessage = '';
    this.selectedActivity = null;
    this.searchText = '';
    this.resetForm();
  }

  loadShortActivities(): void {
    this.modalLoading = true;

    this.additionalActivityService.getAllShort().subscribe({
      next: (data: AdditionalActivityShortResponse[]) => {
        this.allActivities = data;
        this.modalLoading = false;
      },
      error: () => {
        this.modalErrorMessage = 'Greška pri učitavanju aktivnosti.';
        this.modalLoading = false;
      },
    });
  }

  get filteredActivities(): AdditionalActivityShortResponse[] {
    const search = this.searchText.trim().toLowerCase();

    if (!search) {
      return this.allActivities;
    }

    return this.allActivities.filter((activity) => {
      const name = activity.name.toLowerCase();
      const description = activity.description.toLowerCase();

      return name.includes(search) || description.includes(search);
    });
  }

  selectActivity(activity: AdditionalActivityShortResponse): void {
    this.selectedActivity = activity;
    this.modalErrorMessage = '';
  }

  goToFormStep(): void {
    if (!this.selectedActivity) {
      this.modalErrorMessage = 'Izaberite aktivnost pre nastavka.';
      return;
    }

    this.modalErrorMessage = '';
    this.modalStep = 2;
  }

  goBackToSelection(): void {
    this.modalStep = 1;
    this.modalErrorMessage = '';
  }

  addActivityToTerm(): void {
    if (!this.selectedActivity) {
      this.modalErrorMessage = 'Izaberite aktivnost koju želite da dodate.';
      return;
    }

    const request = {
      arrangementTermId: this.arrangementTermId,
      additionalActivityId: this.selectedActivity.id,
      activityDate: this.executionForm.activityDate,
      startTime: this.executionForm.startTime,
      durationMinutes: this.executionForm.durationMinutes,
      capacity: this.executionForm.capacity,
      price: this.executionForm.price,
    };

    if (
      this.executionForm.activityDate < this.termStartDate ||
      this.executionForm.activityDate > this.termEndDate
    ) {
      this.modalErrorMessage = 'Datum aktivnosti mora biti u opsegu termina aranžmana.';
      return;
    }

    this.arrangementService.createActivityExecution(request).subscribe({
      next: () => {
        this.successMessage = 'Aktivnost je uspešno dodata u termin.';
        this.errorMessage = '';
        this.closeAddModal();
        this.loadActivities();

        setTimeout(() => {
          this.successMessage = '';
        }, 3000);
      },
      error: (error) => {
        this.modalErrorMessage = error.error?.message || 'Greška pri dodavanju aktivnosti.';
      },
    });
  }

  resetForm(): void {
    this.executionForm = {
      activityDate: '',
      startTime: '',
      durationMinutes: null,
      capacity: null,
      price: null,
    };
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

  removeActivity(executionId: number): void {
    this.arrangementService.deleteActivityExecution(executionId).subscribe({
      next: () => {
        this.activities = this.activities.filter((activity) => activity.id !== executionId);

        this.successMessage = 'Aktivnost je uspešno uklonjena iz termina.';

        setTimeout(() => {
          this.successMessage = '';
        }, 3000);
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'Greška pri uklanjanju aktivnosti.';
      },
    });
  }
}
