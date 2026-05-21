import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface ManagerArrangementRequest {
  name: string;
  description: string;
  basePrice: number | null;
  numberOfNights: number | null;
  imageUrl: string;
  workflowId: number;
  managerId: number;
  destinationId: number | null;
  accommodationId: number | null;
  transportId: number | null;
}

@Injectable({
  providedIn: 'root',
})
export class ManagerArrangementService {
  private apiUrl = 'http://localhost:8080/api/manager-arrangements';

  constructor(private http: HttpClient) {}

  private getHeaders() {
    const token = localStorage.getItem('token');

    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`,
      }),
    };
  }

  getByManager(managerId: number): Observable<any[]> {
    return this.http.get<any[]>(
      `${this.apiUrl}/manager/${managerId}`,
      this.getHeaders()
    );
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`, this.getHeaders());
  }

  create(request: ManagerArrangementRequest): Observable<any> {
    return this.http.post<any>(this.apiUrl, request, this.getHeaders());
  }

  update(id: number, request: ManagerArrangementRequest): Observable<any> {
    return this.http.put<any>(
      `${this.apiUrl}/${id}`,
      request,
      this.getHeaders()
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, this.getHeaders());
  }

  sendToDirector(id: number, directorUsername: string): Observable<any> {
    return this.http.patch<any>(
      `${this.apiUrl}/${id}/send-to-director`,
      { directorUsername },
      this.getHeaders()
    );
  }
}