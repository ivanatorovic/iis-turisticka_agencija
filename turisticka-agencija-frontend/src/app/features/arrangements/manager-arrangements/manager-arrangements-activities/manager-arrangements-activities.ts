import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { ArrangementActivity, ArrangementService } from '../../../../core/services/arrangement';
import { UserResponse, UserService } from '../../../../core/services/user';
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
  guides: UserResponse[] = [];

  selectedActivity: AdditionalActivityShortResponse | null = null;

  searchText = '';
  modalStep: 1 | 2 = 1;
  showAddModal = false;

  showEditModal = false;
  editErrorMessage = '';

  selectedExecution: ArrangementActivity | null = null;

  editForm = {
    activityDate: '',
    startTime: '',
    durationMinutes: null as number | null,
    capacity: null as number | null,
    guideId: null as number | null,
    price: null as number | null,
  };

  executionForm = {
    activityDate: '',
    startTime: '',
    durationMinutes: null as number | null,
    capacity: null as number | null,
    guideId: null as number | null,
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
    private userService: UserService,
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

  loadGuides(): void {
    this.userService.getGuides().subscribe({
      next: (data: UserResponse[]) => {
        this.guides = data;
      },
      error: (error) => {
        this.modalErrorMessage =
          error.error?.message || error.error || 'Greška pri učitavanju vodiča.';
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

    this.loadGuides();
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

    if (!this.executionForm.guideId) {
      this.modalErrorMessage = 'Izaberite vodiča koji vodi aktivnost.';
      return;
    }

    if (
      this.executionForm.activityDate < this.termStartDate ||
      this.executionForm.activityDate > this.termEndDate
    ) {
      this.modalErrorMessage = 'Datum aktivnosti mora biti u opsegu termina aranžmana.';
      return;
    }

    const request = {
      arrangementTermId: this.arrangementTermId,
      additionalActivityId: this.selectedActivity.id,
      guideId: this.executionForm.guideId,
      activityDate: this.executionForm.activityDate,
      startTime: this.executionForm.startTime,
      durationMinutes: this.executionForm.durationMinutes,
      capacity: this.executionForm.capacity,
      price: this.executionForm.price,
    };

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
      guideId: null,
      price: null,
    };
  }

  openEditModal(activity: ArrangementActivity): void {
    this.selectedExecution = activity;
    this.editErrorMessage = '';

    this.editForm = {
      activityDate: activity.activityDate,
      startTime: activity.startTime?.slice(0, 5),
      durationMinutes: activity.durationMinutes,
      capacity: activity.capacity,
      guideId: activity.guideId,
      price: activity.price,
    };

    this.showEditModal = true;
  }

  closeEditModal(): void {
    this.showEditModal = false;
    this.selectedExecution = null;
    this.editErrorMessage = '';
  }

  saveExecutionChanges(): void {
    if (!this.selectedExecution) {
      return;
    }

    const request = {
      activityDate: this.editForm.activityDate || undefined,
      startTime: this.editForm.startTime || undefined,
      durationMinutes: this.editForm.durationMinutes ?? undefined,
      capacity: this.editForm.capacity ?? undefined,
      guideId: this.editForm.guideId ?? undefined,
      price: this.editForm.price ?? undefined,
    };

    this.arrangementService.updateExecution(this.selectedExecution.id, request).subscribe({
      next: (updatedActivity) => {
        this.activities = this.activities.map((activity) =>
          activity.id === updatedActivity.id ? updatedActivity : activity,
        );

        this.successMessage = 'Aktivnost je uspešno izmenjena.';
        this.closeEditModal();
      },
      error: (error) => {
        this.editErrorMessage = error.error?.message || 'Greška pri izmeni aktivnosti.';
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
