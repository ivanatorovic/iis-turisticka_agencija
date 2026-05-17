import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import {
  Arrangement,
  ArrangementSearchRequest,
  ArrangementService,
} from '../../../core/services/arrangement';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';

@Component({
  selector: 'app-arrangement-search',
  imports: [CommonModule, FormsModule, SidebarMenu,  RouterLink],
  templateUrl: './arrangement-search.html',
  styleUrl: './arrangement-search.css',
})
export class ArrangementSearch implements OnInit {
  arrangements: Arrangement[] = [];
  filtersOpen = false;

  searchRequest: ArrangementSearchRequest = {
    destination: '',
    travelDate: '',
    numberOfPassengers: 1,
    budget: null,

    accommodationCategory: null,
    transportType: null,
    numberOfNights: null,
    additionalService: '',
    sortByPrice: '',
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

      accommodationCategory: null,
      transportType: null,
      numberOfNights: null,
      additionalService: '',
      sortByPrice: '',
    };

    this.filtersOpen = false;
    this.loadAll();
  }

  toggleFilters(): void {
    this.filtersOpen = !this.filtersOpen;
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