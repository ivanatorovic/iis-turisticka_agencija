import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ManagerArrangementService } from '../../../../core/services/manager-arrangement';
import { SidebarMenu } from '../../../../layout/sidebar-menu/sidebar-menu';

@Component({
  selector: 'app-manager-arrangements-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, SidebarMenu],
  templateUrl: './manager-arrangements-list.html',
  styleUrl: './manager-arrangements-list.css',
})
export class ManagerArrangementsList implements OnInit {
  arrangements: any[] = [];
  directorUsername = '';
  selectedArrangementId: number | null = null;

  successMessage = '';
  errorMessage = '';

  constructor(
    private managerArrangementService: ManagerArrangementService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadArrangements();
  }

  loadArrangements(): void {
    const managerId = Number(localStorage.getItem('userId'));

    this.managerArrangementService.getByManager(managerId).subscribe({
      next: (data) => {
        this.arrangements = data;
      },
      error: () => {
        this.errorMessage = 'Greška pri učitavanju aranžmana.';
      },
    });
  }

  editArrangement(arrangement: any): void {
    this.router.navigate([
      '/workflows',
      arrangement.workflow.id,
      'edit-arrangement',
      arrangement.id,
    ]);
  }

  deleteArrangement(id: number): void {
    this.managerArrangementService.delete(id).subscribe({
      next: () => {
        this.successMessage = 'Aranžman je obrisan.';
        this.loadArrangements();
      },
      error: () => {
        this.errorMessage = 'Greška pri brisanju aranžmana.';
      },
    });
  }

  openSendForm(id: number): void {
    this.selectedArrangementId = id;
    this.directorUsername = '';
  }

  cancelSend(): void {
    this.selectedArrangementId = null;
    this.directorUsername = '';
  }

  sendToDirector(id: number): void {
    if (!this.directorUsername.trim()) {
      this.errorMessage = 'Unesi username direktora.';
      return;
    }

    this.managerArrangementService
      .sendToDirector(id, this.directorUsername)
      .subscribe({
        next: () => {
          this.successMessage = 'Aranžman je poslat direktoru.';
          this.selectedArrangementId = null;
          this.directorUsername = '';
          this.loadArrangements();
        },
        error: () => {
          this.errorMessage = 'Greška pri slanju aranžmana direktoru.';
        },
      });
  }

  getStatusLabel(status: string): string {
    if (status === 'DRAFT') return 'Nacrt';
    if (status === 'SENT_TO_DIRECTOR') return 'Poslato direktoru';
    if (status === 'APPROVED') return 'Odobreno';
    if (status === 'REJECTED') return 'Odbijeno';
    if (status === 'PUBLISHED') return 'Objavljeno';
    return status;
  }
}