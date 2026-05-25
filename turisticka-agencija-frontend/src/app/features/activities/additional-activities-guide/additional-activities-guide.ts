import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';
import { ArrangementActivity, ArrangementService } from '../../../core/services/arrangement';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-additional-activities-guide',
  standalone: true,
  imports: [CommonModule, SidebarMenu, RouterLink],
  templateUrl: './additional-activities-guide.html',
  styleUrl: './additional-activities-guide.css',
})
export class AdditionalActivitiesGuide implements OnInit {
  activities: ArrangementActivity[] = [];

  loading = false;
  errorMessage = '';

  constructor(private arrangementService: ArrangementService) {}

  ngOnInit(): void {
    window.scrollTo({
      top: 0,
      behavior: 'instant' as ScrollBehavior,
    });

    this.loadActivities();
  }

  loadActivities(): void {
    this.loading = true;
    this.errorMessage = '';

    this.arrangementService.getGuideActivities().subscribe({
      next: (data: ArrangementActivity[]) => {
        this.activities = data;
        this.sortActivities();
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'Greška pri učitavanju aktivnosti za vodiča.';
        this.loading = false;
      },
    });
  }

  startActivity(id: number): void {
    this.arrangementService.startAdditionalActivityExecution(id).subscribe({
      next: (updatedActivity) => {
        const activity = this.activities.find((a) => a.id === id);

        if (activity) {
          activity.status = updatedActivity.status;
        }

        this.sortActivities();
      },

      error: (error) => {
        this.errorMessage = error.error?.message || 'Greška pri započinjanju aktivnosti.';
      },
    });
  }

  finishActivity(id: number): void {
    this.arrangementService.finishAdditionalActivityExecution(id).subscribe({
      next: (updatedActivity) => {
        const activity = this.activities.find((a) => a.id === id);

        if (activity) {
          activity.status = updatedActivity.status;
        }

        this.sortActivities();
      },

      error: (error) => {
        this.errorMessage = error.error?.message || 'Greška pri završavanju aktivnosti.';
      },
    });
  }

  sortActivities(): void {
    const order: Record<string, number> = {
      ACTIVE: 1,
      UPCOMING: 2,
      FINISHED: 3,
    };

    this.activities.sort((a, b) => {
      return order[a.status] - order[b.status];
    });
  }

  getStatusLabel(status: string): string {
    switch (status) {
      case 'UPCOMING':
        return 'Predstoji';
      case 'ACTIVE':
        return 'U toku';
      case 'FINISHED':
        return 'Završena';
      default:
        return 'Nepoznat status';
    }
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'UPCOMING':
        return 'status-upcoming';
      case 'ACTIVE':
        return 'status-active';
      case 'FINISHED':
        return 'status-finished';
      default:
        return '';
    }
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

  formatDuration(minutes: number): string {
    if (!minutes) {
      return '-';
    }

    const hours = Math.floor(minutes / 60);
    const remainingMinutes = minutes % 60;

    if (hours > 0 && remainingMinutes > 0) {
      return `${hours}h ${remainingMinutes}min`;
    }

    if (hours > 0) {
      return `${hours}h`;
    }

    return `${minutes} min`;
  }
}
