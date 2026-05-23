import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth';

export interface YearlySalesSummary {
  year: number;
  totalReservations: number;
  confirmedReservations: number;
  cancelledReservations: number;
  totalRevenue: number;
  averageReservationValue: number;
}

export interface YearRevenue {
  year: number;
  reservationCount: number;
  revenue: number;
}

export interface MonthlyArrangementSales {
  month: number;
  monthName: string;
  reservationCount: number;
  revenue: number;
}

export interface PopularDestination {
  destinationName: string;
  country: string;
  reservationCount: number;
  revenue: number;
}

export interface PopularArrangement {
  arrangementId: number;
  arrangementName: string;
  reservationCount: number;
  revenue: number;
}

@Injectable({
  providedIn: 'root',
})
export class SalesAnalyticsService {
  private readonly apiUrl = 'http://localhost:8080/api/sales-analytics';

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

  getYearlySummary(year: number): Observable<YearlySalesSummary> {
    const params = new HttpParams().set('year', year);

    return this.http.get<YearlySalesSummary>(`${this.apiUrl}/yearly-summary`, {
      headers: this.getHeaders(),
      params,
    });
  }

  getRevenueByYears(): Observable<YearRevenue[]> {
    return this.http.get<YearRevenue[]>(`${this.apiUrl}/revenue-by-years`, {
      headers: this.getHeaders(),
    });
  }

  getMonthlyArrangementSales(
    arrangementId: number,
    year: number,
  ): Observable<MonthlyArrangementSales[]> {
    const params = new HttpParams()
      .set('arrangementId', arrangementId)
      .set('year', year);

    return this.http.get<MonthlyArrangementSales[]>(`${this.apiUrl}/monthly-arrangement`, {
      headers: this.getHeaders(),
      params,
    });
  }

  getPopularDestinations(year: number): Observable<PopularDestination[]> {
    const params = new HttpParams().set('year', year);

    return this.http.get<PopularDestination[]>(`${this.apiUrl}/popular-destinations`, {
      headers: this.getHeaders(),
      params,
    });
  }

  getPopularArrangements(year: number): Observable<PopularArrangement[]> {
    const params = new HttpParams().set('year', year);

    return this.http.get<PopularArrangement[]>(`${this.apiUrl}/popular-arrangements`, {
      headers: this.getHeaders(),
      params,
    });
  }
}