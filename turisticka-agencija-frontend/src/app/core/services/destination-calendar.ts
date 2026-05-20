import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Destination {
  id: number;
  category: string;
  name: string;
  country: string;
  description: string;
}

export interface DestinationCalendar {
  id: number;
  name: string;
  startDate: string;
  endDate: string;
  seasonType: 'LOW' | 'HIGH' | 'OFF_SEASON';
  status: 'ACTIVE' | 'LOCKED';
  destination: Destination;
}

@Injectable({
  providedIn: 'root',
})
export class DestinationCalendarService {
  private apiUrl = 'http://localhost:8080/api/destination-calendars';

  constructor(private http: HttpClient) {}

private getHeaders() {
  const token = localStorage.getItem('token');

  return {
    headers: new HttpHeaders({
      Authorization: `Bearer ${token}`,
    }),
  };
}
  getAll(): Observable<DestinationCalendar[]> {
    return this.http.get<DestinationCalendar[]>(this.apiUrl, this.getHeaders());
  }

  create(calendar: any): Observable<DestinationCalendar> {
    return this.http.post<DestinationCalendar>(
      this.apiUrl,
      calendar,
      this.getHeaders()
    );
  }

  patch(id: number, updates: any): Observable<DestinationCalendar> {
    return this.http.patch<DestinationCalendar>(
      `${this.apiUrl}/${id}`,
      updates,
      this.getHeaders()
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, this.getHeaders());
  }
}