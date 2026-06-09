import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth';

export interface UserResponse {
  id: number;
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  contact: string | null;
  role: string;
  token: string;
}

export interface UpdateProfileRequest {
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  contact: string | null;
}

export interface UpdateProfileResponse {
  user: UserResponse;
  token: string;
}

export interface ChangePasswordRequest {
  oldPassword: string;
  newPassword: string;
  confirmNewPassword: string;
}

export interface CategoryResponse {
  id: number;
  name: string;
}

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly apiUrl = 'http://localhost:8080/api/users';
  private readonly categoriesUrl = 'http://localhost:8080/api/categories';

  constructor(
    private http: HttpClient,
    private authService: AuthService,
  ) {}

  getMyProfile(): Observable<UserResponse> {
    const token = this.authService.getToken();

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.get<UserResponse>(`${this.apiUrl}/me`, { headers });
  }

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();

    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  getAllCategories(): Observable<CategoryResponse[]> {
    return this.http.get<CategoryResponse[]>(this.categoriesUrl, {
      headers: this.getHeaders(),
    });
  }

  getMyLikedCategories(): Observable<CategoryResponse[]> {
    return this.http.get<CategoryResponse[]>(`${this.apiUrl}/me/liked-categories`, {
      headers: this.getHeaders(),
    });
  }

  addLikedCategory(categoryId: number): Observable<CategoryResponse[]> {
    return this.http.put<CategoryResponse[]>(
      `${this.apiUrl}/me/liked-categories/${categoryId}`,
      {},
      {
        headers: this.getHeaders(),
      },
    );
  }

  removeLikedCategory(categoryId: number): Observable<CategoryResponse[]> {
    return this.http.delete<CategoryResponse[]>(
      `${this.apiUrl}/me/liked-categories/${categoryId}`,
      {
        headers: this.getHeaders(),
      },
    );
  }

  updateMyProfile(request: UpdateProfileRequest): Observable<UpdateProfileResponse> {
    const token = this.authService.getToken();

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.put<UpdateProfileResponse>(`${this.apiUrl}/me`, request, { headers });
  }

  changePassword(request: ChangePasswordRequest): Observable<string> {
    const token = this.authService.getToken();

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.put(`${this.apiUrl}/me/password`, request, {
      headers,
      responseType: 'text',
    });
  }

  getGuides(): Observable<UserResponse[]> {
    const token = this.authService.getToken();

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.get<UserResponse[]>(`${this.apiUrl}/guides`, {
      headers,
    });
  }
}
