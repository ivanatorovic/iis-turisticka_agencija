import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { NgIf } from '@angular/common';
import { AuthService } from '../../core/services/auth';
import { SidebarMenu } from '../../layout/sidebar-menu/sidebar-menu';

@Component({
  selector: 'app-home',
  imports: [RouterLink, NgIf, SidebarMenu],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  constructor(
    private authService: AuthService,
    private router: Router,
  ) {}

  menuOpen = false;

  isLoggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  logout(): void {
    this.closeMenu();
    this.authService.logout();
    this.router.navigate(['/']);
  }

  getUsername(): string {
    return this.authService.getUsername();
  }

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  closeMenu(): void {
    this.menuOpen = false;
  }
}
