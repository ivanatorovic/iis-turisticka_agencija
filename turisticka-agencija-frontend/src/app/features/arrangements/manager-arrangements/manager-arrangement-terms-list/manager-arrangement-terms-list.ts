import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { SidebarMenu } from '../../../../layout/sidebar-menu/sidebar-menu';
import { ArrangementService, ArrangementTermView } from '../../../../core/services/arrangement';

@Component({
  selector: 'app-manager-arrangement-terms-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, SidebarMenu],
  templateUrl: './manager-arrangement-terms-list.html',
  styleUrl: './manager-arrangement-terms-list.css',
})
export class ManagerArrangementTermsList implements OnInit {
  arrangementTerms: ArrangementTermView[] = [];
  arrangements: ArrangementTermView[] = [];

  selectedTermIds: { [arrangementId: number]: number } = {};

  errorMessage = '';

  constructor(private arrangementService: ArrangementService) {}

  ngOnInit(): void {
    this.loadArrangementTerms();
  }

  loadArrangementTerms(): void {
    this.arrangementService.getAllArrangementTerms().subscribe({
      next: (data) => {
        this.arrangementTerms = data;
        this.arrangements = this.getUniqueArrangements(data);

        this.arrangements.forEach((arrangement) => {
          const firstTerm = this.getTermsForArrangement(arrangement.arrangementId)[0];

          if (firstTerm) {
            this.selectedTermIds[arrangement.arrangementId] = firstTerm.arrangementTermId;
          }
        });
      },
      error: () => {
        this.errorMessage = 'Greška pri učitavanju termina aranžmana.';
      },
    });
  }

  getUniqueArrangements(data: ArrangementTermView[]): ArrangementTermView[] {
    const map = new Map<number, ArrangementTermView>();

    data.forEach((item) => {
      if (!map.has(item.arrangementId)) {
        map.set(item.arrangementId, item);
      }
    });

    return Array.from(map.values());
  }

  getTermsForArrangement(arrangementId: number): ArrangementTermView[] {
    return this.arrangementTerms.filter((item) => item.arrangementId === arrangementId);
  }

  getSelectedTerm(arrangementId: number): ArrangementTermView | undefined {
    const selectedTermId = this.selectedTermIds[arrangementId];

    return this.arrangementTerms.find((item) => item.arrangementTermId === selectedTermId);
  }
}
