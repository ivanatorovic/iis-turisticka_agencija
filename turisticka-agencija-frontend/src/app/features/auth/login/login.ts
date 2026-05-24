import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService, LoginRequest } from '../../../core/services/auth';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  form: LoginRequest = {
    username: '',
    password: '',
  };

  errorMessage = '';
  loading = false;

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  login(): void {
    this.errorMessage = '';
    this.loading = true;

    this.authService.login(this.form).subscribe({
      next: (response) => {
        this.loading = false;

        if (response.token) {
          localStorage.setItem('token', response.token);
        }

        if (response.username) {
          localStorage.setItem('username', response.username);
        }

        if (response.id) {
          localStorage.setItem('userId', response.id.toString());
          localStorage.setItem('id', response.id.toString());
        }

        if (response.email) {
          localStorage.setItem('email', response.email);
        }

        if (response.role) {
          localStorage.setItem('role', response.role);
        }

        if (this.isComplaintUser(response.role)) {
          this.router.navigate(['/zalbe']);
          return;
        }

        this.router.navigate(['/']);
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage = err?.error?.message || err?.message || 'Prijava nije uspela.';
      },
    });
  }

  private isComplaintUser(role: string | undefined): boolean {
    return role === 'COMPLAINT_OPERATOR'
      || role === 'COMPLAINT_MANAGER'
      || role === 'COMPLAINT_TEAM_ACCOMMODATION'
      || role === 'COMPLAINT_TEAM_TRANSPORT'
      || role === 'COMPLAINT_TEAM_DOCUMENTATION'
      || role === 'COMPLAINT_TEAM_OTHER'
      || role === 'ADMIN'
      || role === 'DIRECTOR';
  }
}
