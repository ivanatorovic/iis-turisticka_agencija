import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface AdditionalActivityResponse {
  id: number;
  name: string;
  description: string;
  price: number;
  durationMinutes: number;
  location: string;
  imageUrl: string;
  createdById: number;
  createdByUsername: string;
}

export interface AdditionalActivityRequest {
  name: string;
  description: string;
  price: number;
  durationMinutes: number;
  location: string;
  imageUrl: string;
}

@Injectable({
  providedIn: 'root',
})
export class AdditionalActivityService {
  private readonly apiUrl = 'http://localhost:8080/api/additional-activities';

  constructor(private http: HttpClient) {}

  getAll(): Observable<AdditionalActivityResponse[]> {
    const token = localStorage.getItem('token') || localStorage.getItem('jwt');

    return this.http.get<AdditionalActivityResponse[]>(this.apiUrl, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  getById(id: number): Observable<AdditionalActivityResponse> {
    const token = localStorage.getItem('token') || localStorage.getItem('jwt');

    return this.http.get<AdditionalActivityResponse>(`${this.apiUrl}/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  create(formData: FormData): Observable<AdditionalActivityResponse> {
    const token = localStorage.getItem('token') || localStorage.getItem('jwt');

    return this.http.post<AdditionalActivityResponse>(this.apiUrl, formData, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  update(id: number, formData: FormData): Observable<AdditionalActivityResponse> {
    const token = localStorage.getItem('token') || localStorage.getItem('jwt');

    return this.http.put<AdditionalActivityResponse>(`${this.apiUrl}/${id}`, formData, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  delete(id: number): Observable<void> {
    const token = localStorage.getItem('token') || localStorage.getItem('jwt');

    return this.http.delete<void>(`${this.apiUrl}/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }
}
