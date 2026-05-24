import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';
import {
  AdditionalActivityResponse,
  AdditionalActivityService,
} from '../../../core/services/additional.activity';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-additional-activities-manager',
  imports: [CommonModule, SidebarMenu, RouterLink],
  templateUrl: './additional-activities-manager.html',
  styleUrl: './additional-activities-manager.css',
})
export class AdditionalActivitiesManager implements OnInit {
  activities: AdditionalActivityResponse[] = [];
  loading = true;
  errorMessage = '';
  showDeleteModal = false;
  selectedActivityId: number | null = null;

  constructor(private additionalActivityService: AdditionalActivityService) {}

  ngOnInit(): void {
    this.loadActivities();
  }

  loadActivities(): void {
    this.loading = true;
    this.errorMessage = '';

    this.additionalActivityService.getAll().subscribe({
      next: (activities) => {
        this.activities = activities;
        this.loading = false;
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage = err?.error?.message || 'Nije moguće učitati dodatne aktivnosti.';
      },
    });
  }

  getImageUrl(imageUrl: string): string {
    if (!imageUrl) {
      return 'assets/default-activity.jpg';
    }

    return `http://localhost:8080${imageUrl}`;
  }

  formatDuration(minutes: number): string {
    const hours = Math.floor(minutes / 60);
    const remainingMinutes = minutes % 60;

    if (hours > 0 && remainingMinutes > 0) {
      return `${hours}h ${remainingMinutes}min`;
    }

    if (hours > 0) {
      return `${hours}h`;
    }

    return `${minutes}min`;
  }

  deleteActivity(id: number): void {
    const confirmed = confirm('Da li ste sigurni da želite da obrišete ovu dodatnu aktivnost?');

    if (!confirmed) {
      return;
    }

    this.additionalActivityService.delete(id).subscribe({
      next: () => {
        this.activities = this.activities.filter((activity) => activity.id !== id);
      },
      error: (err) => {
        alert(err?.error?.message || 'Došlo je do greške prilikom brisanja aktivnosti.');
      },
    });
  }

  openDeleteModal(id: number): void {
    this.selectedActivityId = id;
    this.showDeleteModal = true;
  }

  closeDeleteModal(): void {
    this.showDeleteModal = false;
    this.selectedActivityId = null;
  }

  confirmDelete(): void {
    if (this.selectedActivityId === null) {
      return;
    }

    this.additionalActivityService.delete(this.selectedActivityId).subscribe({
      next: () => {
        this.activities = this.activities.filter(
          (activity) => activity.id !== this.selectedActivityId,
        );

        this.closeDeleteModal();
      },
      error: (err) => {
        alert(err?.error?.message || 'Došlo je do greške prilikom brisanja aktivnosti.');

        this.closeDeleteModal();
      },
    });
  }
}
