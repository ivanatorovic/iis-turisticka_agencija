import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';

import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';
import {
  AdditionalActivityParticipantResponse,
  ArrangementService,
} from '../../../core/services/arrangement';

@Component({
  selector: 'app-additional-activity-participants',
  standalone: true,
  imports: [CommonModule, SidebarMenu, RouterLink],
  templateUrl: './additional-activity-participants.html',
  styleUrl: './additional-activity-participants.css',
})
export class AdditionalActivityParticipants implements OnInit {
  executionId!: number;

  participants: AdditionalActivityParticipantResponse[] = [];

  loading = false;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private arrangementService: ArrangementService,
  ) {}

  ngOnInit(): void {
    this.executionId = Number(this.route.snapshot.paramMap.get('executionId'));
    this.loadParticipants();
  }

  loadParticipants(): void {
    this.loading = true;
    this.errorMessage = '';

    this.arrangementService.getParticipantsForExecution(this.executionId).subscribe({
      next: (data) => {
        this.participants = data;
        this.loading = false;
      },
      error: (error) => {
        this.errorMessage = error.error?.message || 'Nije moguće učitati učesnike aktivnosti.';
        this.loading = false;
      },
    });
  }

  getStatusLabel(status: string): string {
    if (status === 'ACTIVE') {
      return 'Aktivna';
    }

    if (status === 'CANCELLED') {
      return 'Otkazana';
    }

    return status;
  }
}
