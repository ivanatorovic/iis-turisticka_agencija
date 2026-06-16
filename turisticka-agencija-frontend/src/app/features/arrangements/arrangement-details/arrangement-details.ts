import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
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
  ReservationPassengerRequest,
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

  reservationFormOpen = false;

  passengerFirstName = '';
  passengerLastName = '';
  passengerEmail = '';

  insuranceSelected = false;
  insurancePricePerPerson = 30;

  passengers: ReservationPassengerRequest[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
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
    this.reservationFormOpen = false;
    this.passengers = [];
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

  openReservationForm(): void {
    this.successMessage = '';
    this.errorMessage = '';

    if (!this.arrangement || !this.selectedArrangementTermId) {
      this.errorMessage = 'Morate izabrati termin.';
      return;
    }

    if (this.numberOfPassengers <= 0) {
      this.errorMessage = 'Broj osoba mora biti veći od 0.';
      return;
    }

    this.passengers = [];

    for (let i = 0; i < this.numberOfPassengers; i++) {
      this.passengers.push({
        firstName: '',
        lastName: '',
        age: null,
      });
    }

    this.reservationFormOpen = true;
  }

  closeReservationForm(): void {
    this.reservationFormOpen = false;
  }

  calculatePassengerPrice(passenger: ReservationPassengerRequest): number {
    const selectedTerm = this.getSelectedArrangementTerm();

    if (!selectedTerm || passenger.age === null || passenger.age === undefined) {
      return 0;
    }

    if (passenger.age < 5) {
      return 0;
    }

    if (passenger.age <= 12) {
      return selectedTerm.dynamicPrice * 0.5;
    }

    return selectedTerm.dynamicPrice;
  }

  getPassengerDiscountDescription(passenger: ReservationPassengerRequest): string {
    if (passenger.age === null || passenger.age === undefined) {
      return '';
    }

    if (passenger.age < 5) {
      return 'Dete do 5 godina - gratis';
    }

    if (passenger.age <= 12) {
      return 'Dečiji popust 50%';
    }

    return 'Puna cena';
  }

  calculateTotalPrice(): number {
    if (!this.arrangement) {
      return 0;
    }

    if (this.passengers.length > 0) {
      return this.passengers
        .map((passenger) => this.calculatePassengerPrice(passenger))
        .reduce((sum, price) => sum + price, 0);
    }

    const passengersCount = this.numberOfPassengers > 0 ? this.numberOfPassengers : 1;

    const selectedTerm = this.getSelectedArrangementTerm();

    const pricePerPerson = selectedTerm?.dynamicPrice ?? this.arrangement.basePrice;

    return pricePerPerson * passengersCount;
  }

  calculateInsurancePrice(): number {
    if (!this.insuranceSelected) {
      return 0;
    }

    const passengersCount = this.numberOfPassengers > 0 ? this.numberOfPassengers : 1;

    return this.insurancePricePerPerson * passengersCount;
  }

  calculateFinalPrice(): number {
    return this.calculateTotalPrice() + this.calculateInsurancePrice();
  }

  calculateInstallmentAmount(): number {
    if (this.paymentType !== 'INSTALLMENTS') {
      return this.calculateFinalPrice();
    }

    if (!this.numberOfInstallments || this.numberOfInstallments < 2) {
      return 0;
    }

    return this.calculateFinalPrice() / this.numberOfInstallments;
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

    if (!this.passengerFirstName || !this.passengerLastName || !this.passengerEmail) {
      this.errorMessage = 'Morate popuniti ime, prezime i email nosioca rezervacije.';
      return;
    }

    if (this.passengers.length !== this.numberOfPassengers) {
      this.errorMessage = 'Broj putnika nije ispravan.';
      return;
    }

    for (const passenger of this.passengers) {
      if (
        !passenger.firstName ||
        !passenger.lastName ||
        passenger.age === null ||
        passenger.age === undefined
      ) {
        this.errorMessage = 'Morate popuniti ime, prezime i godine za svakog putnika.';
        return;
      }

      if (passenger.age < 0) {
        this.errorMessage = 'Godine putnika ne mogu biti negativne.';
        return;
      }
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
        numberOfInstallments:
          this.paymentType === 'INSTALLMENTS' ? this.numberOfInstallments : 1,

        passengerFirstName: this.passengerFirstName,
        passengerLastName: this.passengerLastName,
        passengerEmail: this.passengerEmail,

        insuranceSelected: this.insuranceSelected,

        passengers: this.passengers,
      })
      .subscribe({
        next: () => {
          this.successMessage = 'Rezervacija je uspešno kreirana. Preusmeravanje na moje rezervacije...';
          this.errorMessage = '';
          this.reservationFormOpen = false;

          setTimeout(() => {
            this.router.navigate(['/reservations']);
          }, 1200);
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Rezervacija nije moguća. Proverite podatke i dostupnost termina.';
        },
      });
  }

  chooseAlternativeTerm(term: AlternativeTerm): void {
    this.selectedArrangementTermId = term.arrangementTermId;
    this.availabilityResponse = null;
    this.successMessage = '';
    this.errorMessage = '';
    this.reservationFormOpen = false;
    this.passengers = [];
    this.loadActivitiesForSelectedTerm();
  }
}