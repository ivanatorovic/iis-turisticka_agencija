import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService, RegisterRequest } from '../../../core/services/auth';

@Component({
  selector: 'app-register',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
  form: RegisterRequest = {
    firstName: '',
    lastName: '',
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    role: 'CUSTOMER',
    contact: '',
  };

  errorMessage = '';
  successMessage = '';
  loading = false;

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  register(): void {
    this.errorMessage = '';
    this.successMessage = '';

    if (this.form.password !== this.form.confirmPassword) {
      this.errorMessage = 'Lozinke se ne poklapaju.';
      return;
    }

    this.loading = true;

    this.authService.register(this.form).subscribe({
      next: (response) => {
        this.loading = false;
        this.successMessage = 'Registracija je uspešna.';

        if (response.token) localStorage.setItem('token', response.token);
        if (response.username) localStorage.setItem('username', response.username);

        setTimeout(() => {
          this.router.navigate(['/']);
        }, 1200);
      },
      error: (err) => {
        this.loading = false;

        console.log('Cela greška:', err);
        console.log('Poruka sa bekenda:', err?.error?.message);

        this.errorMessage = err?.error?.message || err?.message || 'Registracija nije uspela.';
      },
    });
  }
}
