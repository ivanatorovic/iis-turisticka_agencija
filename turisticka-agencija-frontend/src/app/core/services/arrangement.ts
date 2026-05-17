import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth';

export interface Term {
  id: number;
  startDate: string;
  endDate: string;
}

export interface ArrangementTerm {
  id: number;
  term: Term;
  capacity: number;
  reservedSpots: number;
  availableSpots: number;
}

export interface Destination {
  id: number;
  name: string;
  country: string;
  description: string;
}

export interface Accommodation {
  id: number;
  name: string;
  category: string;
}

export interface Transport {
  id: number;
  type: string;
  company: string;
}

export interface AdditionalService {
  id: number;
  name: string;
}

export interface Arrangement {
  id: number;
  name: string;
  destination: Destination;
  description: string;
  basePrice: number;
  imageUrl: string;
  numberOfNights: number;
  accommodation: Accommodation;
  transport: Transport;
  additionalServices: AdditionalService[];
  arrangementTerms: ArrangementTerm[];
}

export interface ArrangementSearchRequest {
  destination: string;
  travelDate: string;
  numberOfPassengers: number;
  budget: number | null;

  accommodationCategory: string | null;
  transportType: string | null;
  numberOfNights: number | null;
  additionalService: string;
  sortByPrice: string;
}

@Injectable({
  providedIn: 'root',
})
export class ArrangementService {
  private readonly apiUrl = 'http://localhost:8080/api/arrangements';

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

  getAll(): Observable<Arrangement[]> {
    return this.http.get<Arrangement[]>(this.apiUrl, {
      headers: this.getHeaders(),
    });
  }

  search(request: ArrangementSearchRequest): Observable<Arrangement[]> {
    return this.http.post<Arrangement[]>(`${this.apiUrl}/search`, request, {
      headers: this.getHeaders(),
    });
  }

  getById(id: number): Observable<Arrangement> {
    return this.http.get<Arrangement>(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders(),
    });
  }
}