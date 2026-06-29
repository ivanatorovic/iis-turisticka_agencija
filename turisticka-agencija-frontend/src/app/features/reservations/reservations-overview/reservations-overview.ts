import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Reservation, ReservationService } from '../../../core/services/reservation';
import { AuthService } from '../../../core/services/auth';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';
import { Zalba, ZalbaService } from '../../../core/services/zalba';

@Component({
  selector: 'app-reservations-overview',
  imports: [CommonModule, SidebarMenu, RouterLink, FormsModule],
  templateUrl: './reservations-overview.html',
  styleUrl: './reservations-overview.css',
})
export class ReservationsOverview implements OnInit {
  reservations: Reservation[] = [];
  mojeZalbe: Zalba[] = [];

  errorMessage = '';
  successMessage = '';

  ocene: { [zalbaId: number]: number } = {};
  komentariOcene: { [zalbaId: number]: string } = {};

  constructor(
    private reservationService: ReservationService,
    private authService: AuthService,
    private router: Router,
    private zalbaService: ZalbaService,
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
        this.loadMojeZalbe(userId);
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Greška pri učitavanju vaših rezervacija.';
      },
    });
  }

  loadMojeZalbe(userId: number): void {
    this.zalbaService.getByPutnik(userId).subscribe({
      next: (data) => {
        this.mojeZalbe = data;
      },
      error: (err) => {
        console.error(err);
        this.mojeZalbe = [];
      },
    });
  }

  getArrangementId(reservation: Reservation): number | null {
    if (reservation.arrangement && reservation.arrangement.id) {
      return reservation.arrangement.id;
    }

    return null;
  }

  getZalbeForReservation(reservation: Reservation): Zalba[] {
    if (!reservation.id) {
      return [];
    }

    return this.mojeZalbe.filter((zalba) => zalba.reservationId === reservation.id);
  }

  hasComplaintForReservation(reservation: Reservation): boolean {
    return this.getZalbeForReservation(reservation).length > 0;
  }

  canSubmitComplaint(reservation: Reservation): boolean {
    if (this.isSalesAgent()) {
      return false;
    }

    if (reservation.status !== 'CONFIRMED') {
      return false;
    }

    return !this.hasComplaintForReservation(reservation);
  }

  canRateComplaint(zalba: Zalba): boolean {
    return zalba.status === 'ZATVORENO' && !zalba.ocena;
  }

  submitComplaint(reservation: Reservation): void {
    const idTure = this.getArrangementId(reservation);

    if (!idTure) {
      this.errorMessage = 'Nije pronađen ID aranžmana za ovu rezervaciju.';
      return;
    }

    this.router.navigate(['/zalbe'], {
      queryParams: {
        idTure: idTure,
        reservationId: reservation.id,
        nazivTure: reservation.arrangement.name,
      },
    });
  }

  oceniZalbu(zalba: Zalba): void {
    if (!zalba.id) {
      this.errorMessage = 'Nije pronađen ID žalbe.';
      return;
    }

    const ocena = this.ocene[zalba.id];
    const komentar = this.komentariOcene[zalba.id] || '';

    if (!ocena || ocena < 1 || ocena > 5) {
      this.errorMessage = 'Ocena mora biti između 1 i 5.';
      return;
    }

    this.zalbaService.oceni(zalba.id, ocena, komentar).subscribe({
      next: () => {
        this.successMessage = 'Uspešno ste ocenili rešavanje žalbe.';
        this.errorMessage = '';

        delete this.ocene[zalba.id!];
        delete this.komentariOcene[zalba.id!];

        const userId = this.authService.getUserId();
        if (userId) {
          this.loadMojeZalbe(userId);
        }
      },
      error: (err) => {
        console.error(err);
        this.errorMessage = 'Greška pri ocenjivanju žalbe.';
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

  searchTerm = '';

get filteredReservations(): Reservation[] {
  const term = this.searchTerm.toLowerCase().trim();

  if (!term) {
    return this.reservations;
  }

  return this.reservations.filter((reservation) => {
    const arrangementName = reservation.arrangement?.name?.toLowerCase() || '';
    const destination = reservation.arrangement?.destination?.name?.toLowerCase() || '';
    const country = reservation.arrangement?.destination?.country?.toLowerCase() || '';
    const firstName = reservation.passengerFirstName?.toLowerCase() || '';
    const lastName = reservation.passengerLastName?.toLowerCase() || '';
    const email = reservation.passengerEmail?.toLowerCase() || '';
    const status = reservation.status?.toLowerCase() || '';

    return (
      arrangementName.includes(term) ||
      destination.includes(term) ||
      country.includes(term) ||
      firstName.includes(term) ||
      lastName.includes(term) ||
      email.includes(term) ||
      status.includes(term)
    );
  });
}
}
