import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';
import { UserResponse, UserService } from '../../../core/services/user';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-my-profile',
  imports: [CommonModule, FormsModule, RouterLink, SidebarMenu],
  templateUrl: './my-profile.html',
  styleUrl: './my-profile.css',
})
export class MyProfile implements OnInit {
  user: UserResponse | null = null;
  loading = true;
  errorMessage = '';
  editingField: string | null = null;

  successMessage = '';
  saving = false;

  editableUser = {
    firstName: '',
    lastName: '',
    username: '',
    email: '',
    contact: '',
  };

  showOldPassword = false;
  showNewPassword = false;
  showConfirmNewPassword = false;

  changingPassword = false;

  passwordForm = {
    oldPassword: '',
    newPassword: '',
    confirmNewPassword: '',
  };

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getMyProfile().subscribe({
      next: (user) => {
        this.user = user;
        this.loading = false;
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage = err?.error?.message || 'Nije moguće učitati podatke profila.';
      },
    });
  }

  getInitial(): string {
    return this.user?.username?.charAt(0).toUpperCase() || '?';
  }

  startEditing(field: string): void {
    if (!this.user) {
      return;
    }

    this.errorMessage = '';
    this.successMessage = '';
    this.editingField = field;

    this.editableUser = {
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      username: this.user.username,
      email: this.user.email,
      contact: this.user.contact || '',
    };
  }

  cancelEditing(): void {
    this.editingField = null;
  }

  saveChanges(): void {
    if (!this.user) {
      return;
    }

    this.errorMessage = '';
    this.successMessage = '';
    this.saving = true;

    const request = {
      firstName: this.editableUser.firstName,
      lastName: this.editableUser.lastName,
      username: this.editableUser.username,
      email: this.editableUser.email,
      contact: this.editableUser.contact?.trim() || null,
    };

    this.userService.updateMyProfile(request).subscribe({
      next: (response) => {
        this.user = response.user;

        localStorage.setItem('token', response.token);
        localStorage.setItem('username', response.user.username);

        this.successMessage = 'Profil je uspešno ažuriran.';
        this.errorMessage = '';

        this.editingField = null;
        this.saving = false;

        this.clearMessages();
      },
      error: (err) => {
        this.saving = false;
        this.successMessage = '';
        this.errorMessage = err?.error?.message || 'Izmena profila nije uspela.';
        this.clearMessages();
      },
    });
  }

  clearMessages(): void {
    setTimeout(() => {
      this.errorMessage = '';
      this.successMessage = '';
    }, 3000);
  }

  getRoleLabel(role: string): string {
    switch (role) {
      case 'CUSTOMER':
        return 'Kupac';

      case 'ADMIN':
        return 'Administrator';

      case 'SALES_AGENT':
        return 'Prodajni agent';

      case 'MANAGER':
        return 'Menadžer';

      case 'GUIDE':
        return 'Vodič';

      default:
        return role;
    }
  }

  showPasswordForm(): void {
    this.errorMessage = '';
    this.successMessage = '';
    this.changingPassword = true;
  }

  cancelPasswordChange(): void {
    this.changingPassword = false;
    this.passwordForm = {
      oldPassword: '',
      newPassword: '',
      confirmNewPassword: '',
    };
  }

  changePassword(): void {
    this.errorMessage = '';
    this.successMessage = '';

    if (this.passwordForm.newPassword !== this.passwordForm.confirmNewPassword) {
      this.errorMessage = 'Nova lozinka i potvrda lozinke se ne poklapaju.';
      return;
    }

    this.saving = true;

    this.userService.changePassword(this.passwordForm).subscribe({
      next: () => {
        this.saving = false;
        this.successMessage = 'Lozinka je uspešno promenjena.';
        this.cancelPasswordChange();
        this.clearMessages();
      },
      error: (err) => {
        this.saving = false;

        let message = 'Promena lozinke nije uspela.';

        if (typeof err?.error === 'string') {
          try {
            message = JSON.parse(err.error).message || message;
          } catch {
            message = err.error || message;
          }
        } else {
          message = err?.error?.message || message;
        }

        this.errorMessage = message;
        this.clearMessages();
      },
    });
  }
}
