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

        this.router.navigate(['/']);
      },
      error: (err) => {
        this.loading = false;

        this.errorMessage = err?.error?.message || err?.message || 'Prijava nije uspela.';
      },
    });
  }
}
