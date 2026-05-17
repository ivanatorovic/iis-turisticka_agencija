import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Arrangement, ArrangementService } from '../../../core/services/arrangement';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';

@Component({
  selector: 'app-arrangement-details',
  imports: [CommonModule, RouterLink, SidebarMenu],
  templateUrl: './arrangement-details.html',
  styleUrl: './arrangement-details.css',
})
export class ArrangementDetails implements OnInit {
  arrangement: Arrangement | null = null;

  constructor(
    private route: ActivatedRoute,
    private arrangementService: ArrangementService,
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.arrangementService.getById(id).subscribe({
      next: (data) => {
        this.arrangement = data;
      },
      error: (err) => {
        console.error('Greška pri učitavanju detalja aranžmana', err);
      },
    });
  }

  getImageUrl(): string {
    if (!this.arrangement) {
      return '';
    }

    return `http://localhost:8080${this.arrangement.imageUrl}`;
  }
}