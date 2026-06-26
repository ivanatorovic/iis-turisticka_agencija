import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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

export interface AdditionalActivityAnalyticsSummary {
  totalRegistrations: number;
  totalParticipants: number;
  totalRevenue: number;
  averageOccupancy: number;
}

export interface ActivityPopularity {
  activityName: string;
  registrationsCount: number;
  participantsCount: number;
}

export interface ActivityOccupancy {
  activityName: string;
  capacity: number;
  reservedSpots: number;
  occupancyRate: number;
}

export interface ActivityRevenue {
  activityName: string;
  revenue: number;
}

export interface ActivityCancellation {
  activityName: string;
  cancelRate: number;
}

export interface GuideWorkload {
  guideName: string;
  activitiesCount: number;
  participantsCount: number;
}

export interface AdditionalActivityAnalyticsRow {
  arrangementId: number;
  arrangementName: string;
  arrangementTermId: number;
  termStartDate: string;
  termEndDate: string;
  executionId: number;
  activityName: string;
  activityDate: string;
  guideName: string;
  registrationsCount: number;
  participantsCount: number;
  capacity: number;
  reservedSpots: number;
  occupancyRate: number;
  revenue: number;
  cancelRate: number;
}

export interface AdditionalActivityAnalyticsResponse {
  summary: AdditionalActivityAnalyticsSummary;
  popularity: ActivityPopularity[];
  occupancy: ActivityOccupancy[];
  revenue: ActivityRevenue[];
  cancellations: ActivityCancellation[];
  guideWorkload: GuideWorkload[];
  tableRows: AdditionalActivityAnalyticsRow[];
}

export interface AdditionalActivityAnalyticsFilter {
  arrangementId: number;
  arrangementTermId?: number | null;
  dateFrom?: string | null;
  dateTo?: string | null;
  status?: string | null;
}

@Injectable({
  providedIn: 'root',
})
export class AdditionalActivityService {
  private readonly apiUrl = 'http://localhost:8080/api/additional-activities';
  private readonly categoriesUrl = 'http://localhost:8080/api/categories';
  private readonly analyticsUrl = 'http://localhost:8080/api/manager/reports/additional-activities';

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

  getAdditionalActivityAnalytics(
    filters: AdditionalActivityAnalyticsFilter,
  ): Observable<AdditionalActivityAnalyticsResponse> {
    let params = new HttpParams().set('arrangementId', filters.arrangementId);

    if (filters.arrangementTermId) {
      params = params.set('arrangementTermId', filters.arrangementTermId);
    }

    return this.http.get<AdditionalActivityAnalyticsResponse>(this.analyticsUrl, {
      headers: this.getHeaders(),
      params,
    });
  }

  generateAnalyticsPdf(arrangementId: number, arrangementTermId?: number | null): Observable<Blob> {
    let params = new HttpParams().set('arrangementId', arrangementId);

    if (arrangementTermId) {
      params = params.set('arrangementTermId', arrangementTermId);
    }

    return this.http.get(`${this.analyticsUrl}/pdf`, {
      headers: this.getHeaders(),
      params,
      responseType: 'blob',
    });
  }
}
