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
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'Greška pri učitavanju aktivnosti za vodiča.';
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
