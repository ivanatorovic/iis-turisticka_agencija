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

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly apiUrl = 'http://localhost:8080/api/users';

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
}
