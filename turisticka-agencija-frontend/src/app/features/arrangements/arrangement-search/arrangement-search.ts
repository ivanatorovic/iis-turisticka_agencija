import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  Arrangement,
  ArrangementSearchRequest,
  ArrangementService,
} from '../../../core/services/arrangement';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';

@Component({
  selector: 'app-arrangement-search',
  imports: [CommonModule, FormsModule, SidebarMenu],
  templateUrl: './arrangement-search.html',
  styleUrl: './arrangement-search.css',
})
export class ArrangementSearch implements OnInit {
  arrangements: Arrangement[] = [];

  searchRequest: ArrangementSearchRequest = {
    destination: '',
    travelDate: '',
    numberOfPassengers: 1,
    budget: null,
  };

  constructor(private arrangementService: ArrangementService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.arrangementService.getAll().subscribe({
      next: (data) => {
        this.arrangements = data;
      },
      error: (err) => {
        console.error('Greška pri učitavanju aranžmana', err);
      },
    });
  }

  search(): void {
    this.arrangementService.search(this.searchRequest).subscribe({
      next: (data) => {
        this.arrangements = data;
      },
      error: (err) => {
        console.error('Greška pri pretrazi aranžmana', err);
      },
    });
  }

  reset(): void {
    this.searchRequest = {
      destination: '',
      travelDate: '',
      numberOfPassengers: 1,
      budget: null,
    };

    this.loadAll();
  }

  calculateTotalPrice(arrangement: Arrangement): number {
    const passengers =
      this.searchRequest.numberOfPassengers > 0
        ? this.searchRequest.numberOfPassengers
        : 1;

    return arrangement.basePrice * passengers;
  }
  getImageUrl(arrangement: Arrangement): string {
  return `http://localhost:8080${arrangement.imageUrl}`;
}
}