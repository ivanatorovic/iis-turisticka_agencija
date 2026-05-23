import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Reservation, ReservationService } from '../../../core/services/reservation';
import { AuthService } from '../../../core/services/auth';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';

@Component({
  selector: 'app-reservations-overview',
  imports: [CommonModule, SidebarMenu],
  templateUrl: './reservations-overview.html',
  styleUrl: './reservations-overview.css',
})
export class ReservationsOverview implements OnInit {
  reservations: Reservation[] = [];
  errorMessage = '';
  successMessage = '';

  constructor(
    private reservationService: ReservationService,
    private authService: AuthService,
  ) {}

  ngOnInit(): void {
    this.loadReservations();
  }

  isSalesAgent(): boolean {
    return this.authService.getRole() === 'SALES_AGENT';
  }

  getPageTitle(): string {
    return this.isSalesAgent() ? 'Sve rezervacije' : 'Moje rezervacije';
  }

  loadReservations(): void {
    this.errorMessage = '';
    this.successMessage = '';

    if (this.isSalesAgent()) {
      this.reservationService.getAllReservations().subscribe({
        next: (data) => {
          this.reservations = data;
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Greška pri učitavanju svih rezervacija.';
        },
      });

      return;
    }

    const userId = this.authService.getUserId();

    if (!userId) {
      this.errorMessage = 'Korisnik nije prijavljen.';
      return;
    }

    this.reservationService.getMyReservations(userId).subscribe({
      next: (data) => {
        this.reservations = data;
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Greška pri učitavanju vaših rezervacija.';
      },
    });
  }

  cancelReservation(reservationId: number): void {
    this.errorMessage = '';
    this.successMessage = '';

    this.reservationService.cancelReservation(reservationId).subscribe({
      next: () => {
        this.successMessage = 'Rezervacija je uspešno otkazana.';
        this.loadReservations();
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Rezervacija nije mogla biti otkazana.';
      },
    });
  }
}