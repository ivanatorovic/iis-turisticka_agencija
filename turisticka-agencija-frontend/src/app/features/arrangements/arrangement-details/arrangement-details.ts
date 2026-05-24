import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import {
  Arrangement,
  ArrangementActivity,
  ArrangementService,
  ArrangementTerm,
} from '../../../core/services/arrangement';
import {
  AlternativeTerm,
  AvailabilityResponse,
  ReservationService,
} from '../../../core/services/reservation';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';
import { AuthService } from '../../../core/services/auth';

@Component({
  selector: 'app-arrangement-details',
  imports: [CommonModule, RouterLink, FormsModule, SidebarMenu],
  templateUrl: './arrangement-details.html',
  styleUrl: './arrangement-details.css',
})
export class ArrangementDetails implements OnInit {
  arrangement: Arrangement | null = null;

  selectedArrangementTermId: number | null = null;
  selectedArrangementActivities: ArrangementActivity[] = [];

  numberOfPassengers = 1;
  availabilityResponse: AvailabilityResponse | null = null;

  paymentType = 'ONE_TIME';
  numberOfInstallments: number | null = null;

  successMessage = '';
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private arrangementService: ArrangementService,
    private reservationService: ReservationService,
    private authService: AuthService,
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.arrangementService.getById(id).subscribe({
      next: (data) => {
        this.arrangement = data;

        if (data.arrangementTerms && data.arrangementTerms.length > 0) {
          this.selectedArrangementTermId = data.arrangementTerms[0].id;
          this.loadActivitiesForSelectedTerm();
        }
      },
      error: (err) => {
        console.error('Greška pri učitavanju detalja aranžmana', err);
      },
    });
  }

  loadActivitiesForSelectedTerm(): void {
    if (!this.selectedArrangementTermId) {
      this.selectedArrangementActivities = [];
      return;
    }

    this.arrangementService
      .getActivitiesForArrangementTerm(this.selectedArrangementTermId)
      .subscribe({
        next: (activities) => {
          this.selectedArrangementActivities = activities;
        },
        error: (err) => {
          console.error('Greška pri učitavanju dodatnih aktivnosti', err);
          this.selectedArrangementActivities = [];
        },
      });
  }

  onArrangementTermChange(): void {
    this.loadActivitiesForSelectedTerm();
    this.availabilityResponse = null;
    this.successMessage = '';
    this.errorMessage = '';
  }

  getImageUrl(): string {
    if (!this.arrangement) {
      return '';
    }

    return `http://localhost:8080${this.arrangement.imageUrl}`;
  }

  getSelectedArrangementTerm(): ArrangementTerm | undefined {
    return this.arrangement?.arrangementTerms.find(
      (arrangementTerm) => arrangementTerm.id === this.selectedArrangementTermId,
    );
  }

  calculateTotalPrice(): number {
    if (!this.arrangement) {
      return 0;
    }

    const passengers = this.numberOfPassengers > 0 ? this.numberOfPassengers : 1;

    return this.arrangement.basePrice * passengers;
  }

  checkAvailability(): void {
    this.successMessage = '';
    this.errorMessage = '';
    this.availabilityResponse = null;

    if (!this.arrangement || !this.selectedArrangementTermId) {
      this.errorMessage = 'Morate izabrati termin.';
      return;
    }

    if (this.numberOfPassengers <= 0) {
      this.errorMessage = 'Broj osoba mora biti veći od 0.';
      return;
    }

    this.reservationService
      .checkAvailability(
        this.arrangement.id,
        this.selectedArrangementTermId,
        this.numberOfPassengers,
      )
      .subscribe({
        next: (response) => {
          this.availabilityResponse = response;

          if (response.available) {
            this.successMessage = `Termin je dostupan. Slobodnih mesta: ${response.availableSpots}.`;
          } else {
            this.errorMessage = `Nema dovoljno mesta. Slobodnih mesta u izabranom terminu: ${response.availableSpots}.`;
          }
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Došlo je do greške pri proveri dostupnosti.';
        },
      });
  }

  reserve(): void {
    this.successMessage = '';
    this.errorMessage = '';

    if (!this.arrangement || !this.selectedArrangementTermId) {
      this.errorMessage = 'Morate izabrati termin.';
      return;
    }

    const userId = this.authService.getUserId();

    if (!userId) {
      this.errorMessage = 'Morate biti prijavljeni da biste rezervisali aranžman.';
      return;
    }

    this.reservationService
      .createReservation({
        userId,
        arrangementId: this.arrangement.id,
        arrangementTermId: this.selectedArrangementTermId,
        numberOfPassengers: this.numberOfPassengers,
        paymentType: this.paymentType,
        numberOfInstallments: this.paymentType === 'INSTALLMENTS' ? this.numberOfInstallments : 1,
      })
      .subscribe({
        next: () => {
          this.successMessage = 'Rezervacija je uspešno kreirana.';
          this.availabilityResponse = null;

          this.arrangementService.getById(this.arrangement!.id).subscribe({
            next: (updatedArrangement) => {
              this.arrangement = updatedArrangement;
              this.loadActivitiesForSelectedTerm();
            },
          });
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Rezervacija nije moguća. Proverite dostupnost termina.';
        },
      });
  }

  chooseAlternativeTerm(term: AlternativeTerm): void {
    this.selectedArrangementTermId = term.arrangementTermId;
    this.availabilityResponse = null;
    this.successMessage = '';
    this.errorMessage = '';
    this.loadActivitiesForSelectedTerm();
  }

  calculateInstallmentAmount(): number {
    if (this.paymentType !== 'INSTALLMENTS') {
      return this.calculateTotalPrice();
    }

    if (!this.numberOfInstallments || this.numberOfInstallments < 2) {
      return 0;
    }

    return this.calculateTotalPrice() / this.numberOfInstallments;
  }
}
