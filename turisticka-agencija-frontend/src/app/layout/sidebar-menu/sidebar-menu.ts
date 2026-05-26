import { Component } from '@angular/core';
import { NgIf } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth';

@Component({
  selector: 'app-sidebar-menu',
  standalone: true,
  imports: [NgIf, RouterLink],
  templateUrl: './sidebar-menu.html',
  styleUrl: './sidebar-menu.css',
})
export class SidebarMenu {
  menuOpen = false;

  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  getUsername(): string {
    return this.authService.getUsername();
  }

  getRole(): string | null {
    return this.authService.getRole();
  }

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  closeMenu(): void {
    this.menuOpen = false;
  }

  logout(): void {
    this.closeMenu();
    this.authService.logout();
    this.router.navigate(['/']);
  }

  isCustomer(): boolean {
    return this.getRole() === 'CUSTOMER';
  }

  isSalesAgent(): boolean {
    return this.getRole() === 'SALES_AGENT';
  }

  isManager(): boolean {
    return this.getRole() === 'MANAGER';
  }

  isAdmin(): boolean {
    return this.getRole() === 'ADMIN';
  }

  isGuide(): boolean {
    return this.getRole() === 'GUIDE';
  }

  isComplaintOperator(): boolean {
    return this.getRole() === 'COMPLAINT_OPERATOR';
  }

  isComplaintTeam(): boolean {
    return (
      this.getRole() === 'COMPLAINT_TEAM_ACCOMMODATION' ||
      this.getRole() === 'COMPLAINT_TEAM_TRANSPORT' ||
      this.getRole() === 'COMPLAINT_TEAM_DOCUMENTATION' ||
      this.getRole() === 'COMPLAINT_TEAM_OTHER'
    );
  }

  isComplaintManager(): boolean {
    return this.getRole() === 'COMPLAINT_MANAGER';
  }

  isDirector(): boolean {
    return this.getRole() === 'DIRECTOR';
  }
}
