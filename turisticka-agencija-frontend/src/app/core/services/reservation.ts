import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth';

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

export interface CreateReservationRequest {
  userId: number;
  arrangementId: number;
  arrangementTermId: number;
  numberOfPassengers: number;
}

export interface Reservation {
  id: number;
  numberOfPassengers: number;
  totalPrice: number;
  reservationDate: string;
  status: string;
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

  checkAvailability(
    arrangementId: number,
    arrangementTermId: number,
    passengers: number,
  ): Observable<AvailabilityResponse> {
    const params = new HttpParams()
      .set('arrangementId', arrangementId)
      .set('arrangementTermId', arrangementTermId)
      .set('passengers', passengers);

    return this.http.get<AvailabilityResponse>(`${this.apiUrl}/check-availability`, {
      headers: this.getHeaders(),
      params,
    });
  }

  createReservation(request: CreateReservationRequest): Observable<Reservation> {
    return this.http.post<Reservation>(this.apiUrl, request, {
      headers: this.getHeaders(),
    });
  }
}