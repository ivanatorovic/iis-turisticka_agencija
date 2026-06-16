import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth';
import { Arrangement, ArrangementTerm } from './arrangement';

export interface ReservationPassengerRequest {
  firstName: string;
  lastName: string;
  age: number | null;
}

export interface ReservationPassenger {
  id: number;
  firstName: string;
  lastName: string;
  age: number;
  price: number;
  discountDescription: string;
}

export interface CreateReservationRequest {
  userId: number;
  arrangementId: number;
  arrangementTermId: number;
  numberOfPassengers: number;
  paymentType: string;
  numberOfInstallments: number | null;

  passengerFirstName: string;
  passengerLastName: string;
  passengerEmail: string;

  insuranceSelected: boolean;

  passengers: ReservationPassengerRequest[];
}

export interface AlternativeTerm {
  arrangementTermId: number;
  startDate: string;
  endDate: string;
  availableSpots: number;
}

export interface AvailabilityResponse {
  available: boolean;
  availableSpots: number;
  alternativeTerms: AlternativeTerm[];
}

export interface Reservation {
  id: number;
  numberOfPassengers: number;
  totalPrice: number;
  reservationDate: string;
  status: string;

  user: any;
  arrangement: Arrangement;
  arrangementTerm: ArrangementTerm;

  paymentType: string;
  numberOfInstallments: number;
  installmentAmount: number;

  passengerFirstName: string;
  passengerLastName: string;
  passengerEmail: string;

  basePricePerPerson: number;
  dynamicPricePerPerson: number;

  insuranceSelected: boolean;
  insurancePrice: number;

  arrangementTotalPrice: number;

  passengers: ReservationPassenger[];
}

@Injectable({
  providedIn: 'root',
})
export class ReservationService {
  private readonly apiUrl = 'http://localhost:8080/api/reservations';

  constructor(
    private http: HttpClient,
    private authService: AuthService,
  ) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();

    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  getAllReservations(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(this.apiUrl, {
      headers: this.getHeaders(),
    });
  }

  getMyReservations(userId: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.apiUrl}/my?userId=${userId}`, {
      headers: this.getHeaders(),
    });
  }

  checkAvailability(
    arrangementId: number,
    arrangementTermId: number,
    passengers: number,
  ): Observable<AvailabilityResponse> {
    return this.http.get<AvailabilityResponse>(
      `${this.apiUrl}/check-availability?arrangementId=${arrangementId}&arrangementTermId=${arrangementTermId}&passengers=${passengers}`,
      {
        headers: this.getHeaders(),
      },
    );
  }

  createReservation(request: CreateReservationRequest): Observable<Reservation> {
    return this.http.post<Reservation>(this.apiUrl, request, {
      headers: this.getHeaders(),
    });
  }

  cancelReservation(reservationId: number): Observable<Reservation> {
    return this.http.put<Reservation>(
      `${this.apiUrl}/${reservationId}/cancel`,
      {},
      {
        headers: this.getHeaders(),
      },
    );
  }
}