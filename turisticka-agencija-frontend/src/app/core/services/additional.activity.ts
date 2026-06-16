import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './auth';

export interface CategoryResponse {
  id: number;
  name: string;
}

export interface AdditionalActivityResponse {
  id: number;
  name: string;
  description: string;
  location: string;
  imageUrl: string;
  createdById: number;
  createdByUsername: string;
  categories: CategoryResponse[];
}

export interface AdditionalActivityRequest {
  name: string;
  description: string;
  location: string;
  imageUrl: string;
  categoryIds?: number[];
}

export interface AdditionalActivityShortResponse {
  id: number;
  name: string;
  description: string;
  location: string;
}

@Injectable({
  providedIn: 'root',
})
export class AdditionalActivityService {
  private readonly apiUrl = 'http://localhost:8080/api/additional-activities';
  private readonly categoriesUrl = 'http://localhost:8080/api/categories';

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

  getAll(): Observable<AdditionalActivityResponse[]> {
    return this.http.get<AdditionalActivityResponse[]>(this.apiUrl, {
      headers: this.getHeaders(),
    });
  }

  getById(id: number): Observable<AdditionalActivityResponse> {
    return this.http.get<AdditionalActivityResponse>(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders(),
    });
  }

  create(formData: FormData): Observable<AdditionalActivityResponse> {
    return this.http.post<AdditionalActivityResponse>(this.apiUrl, formData, {
      headers: this.getHeaders(),
    });
  }

  update(id: number, formData: FormData): Observable<AdditionalActivityResponse> {
    return this.http.put<AdditionalActivityResponse>(`${this.apiUrl}/${id}`, formData, {
      headers: this.getHeaders(),
    });
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders(),
    });
  }

  getAllShort(): Observable<AdditionalActivityShortResponse[]> {
    return this.http.get<AdditionalActivityShortResponse[]>(`${this.apiUrl}/short`, {
      headers: this.getHeaders(),
    });
  }

  getAllCategories(): Observable<CategoryResponse[]> {
    return this.http.get<CategoryResponse[]>(this.categoriesUrl, {
      headers: this.getHeaders(),
    });
  }

  addCategoryToActivity(
    activityId: number,
    categoryId: number,
  ): Observable<AdditionalActivityResponse> {
    return this.http.put<AdditionalActivityResponse>(
      `${this.apiUrl}/${activityId}/categories/${categoryId}`,
      {},
      { headers: this.getHeaders() },
    );
  }

  removeCategoryFromActivity(
    activityId: number,
    categoryId: number,
  ): Observable<AdditionalActivityResponse> {
    return this.http.delete<AdditionalActivityResponse>(
      `${this.apiUrl}/${activityId}/categories/${categoryId}`,
      { headers: this.getHeaders() },
    );
  }
}
