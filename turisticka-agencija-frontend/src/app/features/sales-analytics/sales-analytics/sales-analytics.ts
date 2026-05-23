import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  SalesAnalyticsService,
  YearlySalesSummary,
  YearRevenue,
  MonthlyArrangementSales,
  PopularDestination,
  PopularArrangement,
} from '../../../core/services/sales-analytics';
import { Arrangement, ArrangementService } from '../../../core/services/arrangement';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';

@Component({
  selector: 'app-sales-analytics',
  imports: [CommonModule, FormsModule, SidebarMenu],
  templateUrl: './sales-analytics.html',
  styleUrl: './sales-analytics.css',
})
export class SalesAnalytics implements OnInit {
  selectedYear = 2026;
  selectedArrangementId: number | null = null;

  arrangements: Arrangement[] = [];

  yearlySummary: YearlySalesSummary | null = null;
  revenueByYears: YearRevenue[] = [];
  monthlyArrangementSales: MonthlyArrangementSales[] = [];
  popularDestinations: PopularDestination[] = [];
  popularArrangements: PopularArrangement[] = [];

  errorMessage = '';

  constructor(
    private salesAnalyticsService: SalesAnalyticsService,
    private arrangementService: ArrangementService,
  ) {}

  ngOnInit(): void {
    this.loadArrangements();
    this.loadAnalytics();
  }

  loadArrangements(): void {
    this.arrangementService.getAll().subscribe({
      next: (data) => {
        this.arrangements = data;

        if (data.length > 0) {
          this.selectedArrangementId = data[0].id;
          this.loadMonthlyArrangementSales();
        }
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  loadAnalytics(): void {
    this.errorMessage = '';

    this.salesAnalyticsService.getYearlySummary(this.selectedYear).subscribe({
      next: (data) => {
        this.yearlySummary = data;
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Greška pri učitavanju godišnjeg pregleda.';
      },
    });

    this.salesAnalyticsService.getRevenueByYears().subscribe({
      next: (data) => {
        this.revenueByYears = data;
      },
      error: (err) => {
        console.error(err);
      },
    });

    this.salesAnalyticsService.getPopularDestinations(this.selectedYear).subscribe({
      next: (data) => {
        this.popularDestinations = data;
      },
      error: (err) => {
        console.error(err);
      },
    });

    this.salesAnalyticsService.getPopularArrangements(this.selectedYear).subscribe({
      next: (data) => {
        this.popularArrangements = data;
      },
      error: (err) => {
        console.error(err);
      },
    });

    this.loadMonthlyArrangementSales();
  }

  loadMonthlyArrangementSales(): void {
    if (!this.selectedArrangementId) {
      return;
    }

    this.salesAnalyticsService
      .getMonthlyArrangementSales(this.selectedArrangementId, this.selectedYear)
      .subscribe({
        next: (data) => {
          this.monthlyArrangementSales = data;
        },
        error: (err) => {
          console.error(err);
        },
      });
  }

  onYearChange(): void {
    this.loadAnalytics();
  }

  onArrangementChange(): void {
    this.loadMonthlyArrangementSales();
  }
}