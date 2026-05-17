import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  password: string;
  confirmPassword: string;
  role: string;
  contact: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  id?: number;
  token?: string;
  username?: string;
  email?: string;
  role?: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  register(request: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, request);
  }

  login(request: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, request);
  }

  saveAuthData(response: AuthResponse): void {
  if (response.token) {
    localStorage.setItem('token', response.token);
  }

  if (response.username) {
    localStorage.setItem('username', response.username);
  }

  if (response.id) {
    localStorage.setItem('userId', response.id.toString());
  }

  if (response.role) {
    localStorage.setItem('role', response.role);
  }
}

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  getUsername(): string {
    return localStorage.getItem('username') || 'Profil';
  }

  getUserId(): number | null {
  const id = localStorage.getItem('userId');

  return id ? Number(id) : null;
}

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('userId');
    localStorage.removeItem('role');
  }

  

  getToken(): string | null {
    return localStorage.getItem('token');
  }
}