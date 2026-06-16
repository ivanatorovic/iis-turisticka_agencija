import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
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

export interface ArrangementTermView {
  arrangementTermId: number;

  arrangementId: number;
  arrangementName: string;
  destinationName: string;
  destinationCountry: string;
  basePrice: number;
  numberOfNights: number;

  termId: number;
  startDate: string;
  endDate: string;

  capacity: number;
  reservedSpots: number;
  availableSpots: number;
}

export interface ArrangementActivity {
  id: number;

  arrangementTermId: number;
  arrangementName: string;
  arrangementStartDate: string;
  arrangementEndDate: string;
  additionalActivityId: number;

  activityName: string;
  activityDescription: string;
  activityLocation: string;

  imageUrl: string;

  activityDate: string;
  startTime: string;

  durationMinutes: number;

  capacity: number;
  reservedSpots: number;
  availableSpots: number;

  price: number;
  guideId: number;
  guideFirstName: string;
  guideLastName: string;
  guideUsername: string;
  status: string;
  prior: boolean;
}

export interface AdditionalActivityExecutionRequest {
  arrangementTermId: number;
  additionalActivityId: number;
  activityDate: string;
  startTime: string;
  durationMinutes: number | null;
  capacity: number | null;
  price: number | null;
  guideId: number | null;
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

export interface AdditionalActivityRegistrationRequest {
  numberOfParticipants: number | null;
}

export interface AdditionalActivityRegistrationResponse {
  id: number;
  executionId: number;

  activityName: string;
  activityDescription: string;
  activityLocation: string;
  imageUrl: string;

  activityDate: string;
  startTime: string;
  durationMinutes: number;

  numberOfParticipants: number;
  price: number;
  registrationDate: string;
  status: string;
}

export interface AdditionalActivityRegistrationUpdateRequest {
  numberOfParticipants: number | null;
}

export interface AdditionalActivityParticipantResponse {
  registrationId: number;
  userId: number;
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  contact: string;
  numberOfParticipants: number;
  registrationDate: string;
  status: string;
}

export interface AdditionalActivityExecutionUpdateRequest {
  activityDate?: string;
  startTime?: string;
  durationMinutes?: number;
  capacity?: number;
  guideId?: number;
  price?: number;
}

export interface AdditionalActivityExecutionFilter {
  dateFrom?: string;
  dateTo?: string;
  minPrice?: number | null;
  maxPrice?: number | null;
  minDuration?: number | null;
  maxDuration?: number | null;
  minAvailableSpots?: number | null;
  onlyAvailable?: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class ArrangementService {
  private readonly apiUrl = 'http://localhost:8080/api/arrangements';

  private readonly arrangementTermsUrl = 'http://localhost:8080/api/arrangement-terms';

  private readonly additionalActivityExecutionsUrl =
    'http://localhost:8080/api/additional-activity-executions';

  private readonly additionalActivityRegistrationsUrl =
    'http://localhost:8080/api/additional-activity-registrations';

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

  getAllArrangementTerms(): Observable<ArrangementTermView[]> {
    return this.http.get<ArrangementTermView[]>(this.arrangementTermsUrl, {
      headers: this.getHeaders(),
    });
  }

  getActivitiesForArrangementTerm(arrangementTermId: number): Observable<ArrangementActivity[]> {
    return this.http.get<ArrangementActivity[]>(
      `${this.additionalActivityExecutionsUrl}/arrangement-term/${arrangementTermId}`,
      {
        headers: this.getHeaders(),
      },
    );
  }

  createActivityExecution(
    request: AdditionalActivityExecutionRequest,
  ): Observable<ArrangementActivity> {
    return this.http.post<ArrangementActivity>(this.additionalActivityExecutionsUrl, request, {
      headers: this.getHeaders(),
    });
  }

  deleteActivityExecution(id: number): Observable<void> {
    return this.http.delete<void>(`${this.additionalActivityExecutionsUrl}/${id}`, {
      headers: this.getHeaders(),
    });
  }

  getMyActivityRegistrations(): Observable<AdditionalActivityRegistrationResponse[]> {
    return this.http.get<AdditionalActivityRegistrationResponse[]>(
      `${this.additionalActivityRegistrationsUrl}/my`,
      {
        headers: this.getHeaders(),
      },
    );
  }

  registerForActivity(
    executionId: number,
    request: AdditionalActivityRegistrationRequest,
  ): Observable<AdditionalActivityRegistrationResponse> {
    return this.http.post<AdditionalActivityRegistrationResponse>(
      `${this.additionalActivityRegistrationsUrl}/execution/${executionId}`,
      request,
      {
        headers: this.getHeaders(),
      },
    );
  }

  cancelActivityRegistration(registrationId: number): Observable<void> {
    return this.http.put<void>(
      `${this.additionalActivityRegistrationsUrl}/${registrationId}/cancel`,
      {},
      {
        headers: this.getHeaders(),
      },
    );
  }

  updateExecution(
    id: number,
    request: AdditionalActivityExecutionUpdateRequest,
  ): Observable<ArrangementActivity> {
    return this.http.put<ArrangementActivity>(
      `${this.additionalActivityExecutionsUrl}/${id}`,
      request,
      {
        headers: this.getHeaders(),
      },
    );
  }

  startAdditionalActivityExecution(id: number) {
    return this.http.put<ArrangementActivity>(
      `${this.additionalActivityExecutionsUrl}/${id}/start`,
      {},
      {
        headers: this.getHeaders(),
      },
    );
  }

  getRecommendedSortedActivitiesForArrangementTerm(
    arrangementTermId: number,
  ): Observable<ArrangementActivity[]> {
    return this.http.get<ArrangementActivity[]>(
      `${this.additionalActivityExecutionsUrl}/arrangement-term/${arrangementTermId}/recommended-sorted`,
      {
        headers: this.getHeaders(),
      },
    );
  }

  finishAdditionalActivityExecution(id: number) {
    return this.http.put<ArrangementActivity>(
      `${this.additionalActivityExecutionsUrl}/${id}/finish`,
      {},
      {
        headers: this.getHeaders(),
      },
    );
  }

  updateActivityRegistration(
    registrationId: number,
    request: AdditionalActivityRegistrationUpdateRequest,
  ): Observable<AdditionalActivityRegistrationResponse> {
    return this.http.put<AdditionalActivityRegistrationResponse>(
      `${this.additionalActivityRegistrationsUrl}/${registrationId}`,
      request,
      {
        headers: this.getHeaders(),
      },
    );
  }

  getGuideActivities(): Observable<ArrangementActivity[]> {
    return this.http.get<ArrangementActivity[]>(`${this.additionalActivityExecutionsUrl}/guide`, {
      headers: this.getHeaders(),
    });
  }

  getParticipantsForExecution(
    executionId: number,
  ): Observable<AdditionalActivityParticipantResponse[]> {
    return this.http.get<AdditionalActivityParticipantResponse[]>(
      `${this.additionalActivityRegistrationsUrl}/execution/${executionId}/participants`,
      {
        headers: this.getHeaders(),
      },
    );
  }

  getFilteredActivitiesForArrangementTerm(
    arrangementTermId: number,
    filter: AdditionalActivityExecutionFilter,
  ): Observable<ArrangementActivity[]> {
    let params = new HttpParams();

    if (filter.dateFrom) {
      params = params.set('dateFrom', filter.dateFrom);
    }

    if (filter.dateTo) {
      params = params.set('dateTo', filter.dateTo);
    }

    if (filter.minPrice !== null && filter.minPrice !== undefined) {
      params = params.set('minPrice', filter.minPrice);
    }

    if (filter.maxPrice !== null && filter.maxPrice !== undefined) {
      params = params.set('maxPrice', filter.maxPrice);
    }

    if (filter.minDuration !== null && filter.minDuration !== undefined) {
      params = params.set('minDuration', filter.minDuration);
    }

    if (filter.maxDuration !== null && filter.maxDuration !== undefined) {
      params = params.set('maxDuration', filter.maxDuration);
    }

    if (filter.minAvailableSpots !== null && filter.minAvailableSpots !== undefined) {
      params = params.set('minAvailableSpots', filter.minAvailableSpots);
    }

    params = params.set('onlyAvailable', filter.onlyAvailable ?? false);

    return this.http.get<ArrangementActivity[]>(
      `${this.additionalActivityExecutionsUrl}/arrangement-term/${arrangementTermId}/filter`,
      {
        headers: this.getHeaders(),
        params,
      },
    );
  }

  setExecutionPrior(executionId: number, prior: boolean): Observable<ArrangementActivity> {
    return this.http.put<ArrangementActivity>(
      `${this.additionalActivityExecutionsUrl}/${executionId}/prior?prior=${prior}`,
      {},
      {
        headers: this.getHeaders(),
      },
    );
  }
}
