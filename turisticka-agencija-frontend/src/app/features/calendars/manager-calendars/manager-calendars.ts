import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';
import {
  DestinationCalendar,
  DestinationCalendarService,
} from '../../../core/services/destination-calendar';
import { DestinationService } from '../../../core/services/destination';

interface Destination {
  id: number;
  category: string;
  name: string;
  country: string;
  description: string;
}

@Component({
  selector: 'app-manager-calendars',
  imports: [CommonModule, FormsModule, SidebarMenu],
  templateUrl: './manager-calendars.html',
  styleUrl: './manager-calendars.css',
})
export class ManagerCalendars implements OnInit {
  calendars: DestinationCalendar[] = [];
  destinations: Destination[] = [];

  newCalendar = {
    name: '',
    startDate: '',
    endDate: '',
    seasonType: 'HIGH',
    status: 'ACTIVE',
    destinationId: null as number | null,
  };

  editingCalendarId: number | null = null;

  editCalendar = {
    name: '',
    startDate: '',
    endDate: '',
    seasonType: 'HIGH',
    status: 'ACTIVE',
    destinationId: null as number | null,
  };

  successMessage = '';
  errorMessage = '';

  constructor(
    private calendarService: DestinationCalendarService,
    private destinationService: DestinationService
  ) {}

  ngOnInit(): void {
    this.loadCalendars();
    this.loadDestinations();
  }

  loadCalendars(): void {
    this.calendarService.getAll().subscribe({
      next: (data: DestinationCalendar[]) => {
        this.calendars = data;
      },
      error: (err: any) => {
        console.error(err);
        this.showError('Greška pri učitavanju kalendara.');
      },
    });
  }

  loadDestinations(): void {
    this.destinationService.getAll().subscribe({
      next: (data: Destination[]) => {
        this.destinations = data;
      },
      error: (err: any) => {
        console.error(err);
        this.showError('Greška pri učitavanju destinacija.');
      },
    });
  }

  addCalendar(): void {
    if (
      !this.newCalendar.name.trim() ||
      !this.newCalendar.startDate ||
      !this.newCalendar.endDate ||
      this.newCalendar.destinationId === null
    ) {
      this.showError('Popuni sva obavezna polja.');
      return;
    }

    const request = {
      name: this.newCalendar.name.trim(),
      startDate: this.newCalendar.startDate,
      endDate: this.newCalendar.endDate,
      seasonType: this.newCalendar.seasonType,
      status: this.newCalendar.status,
      destination: {
        id: this.newCalendar.destinationId,
      },
    };

    this.calendarService.create(request).subscribe({
      next: () => {
        this.loadCalendars();
        this.resetNewCalendar();
        this.showSuccess('Kalendar je uspešno dodat.');
      },
      error: (err: any) => {
        console.error(err);
        this.showError('Greška pri dodavanju kalendara.');
      },
    });
  }

  startEdit(calendar: DestinationCalendar): void {
    this.editingCalendarId = calendar.id;

    this.editCalendar = {
      name: calendar.name,
      startDate: calendar.startDate,
      endDate: calendar.endDate,
      seasonType: calendar.seasonType,
      status: calendar.status,
      destinationId: calendar.destination.id,
    };
  }

  cancelEdit(): void {
    this.editingCalendarId = null;
  }

  saveEdit(calendarId: number): void {
    if (
      !this.editCalendar.name.trim() ||
      !this.editCalendar.startDate ||
      !this.editCalendar.endDate ||
      this.editCalendar.destinationId === null
    ) {
      this.showError('Popuni sva obavezna polja.');
      return;
    }

    const updates = {
      name: this.editCalendar.name.trim(),
      startDate: this.editCalendar.startDate,
      endDate: this.editCalendar.endDate,
      seasonType: this.editCalendar.seasonType,
      status: this.editCalendar.status,
      destination: {
        id: this.editCalendar.destinationId,
      },
    };

    this.calendarService.patch(calendarId, updates).subscribe({
      next: () => {
        this.loadCalendars();
        this.cancelEdit();
        this.showSuccess('Kalendar je izmenjen.');
      },
      error: (err: any) => {
        console.error(err);
        this.showError('Greška pri izmeni kalendara.');
      },
    });
  }

  deleteCalendar(id: number): void {
    this.calendarService.delete(id).subscribe({
      next: () => {
        this.loadCalendars();
        this.showSuccess('Kalendar je obrisan.');
      },
      error: (err: any) => {
        console.error(err);
        this.showError('Greška pri brisanju kalendara.');
      },
    });
  }

  resetNewCalendar(): void {
    this.newCalendar = {
      name: '',
      startDate: '',
      endDate: '',
      seasonType: 'HIGH',
      status: 'ACTIVE',
      destinationId: null,
    };
  }

  getSeasonLabel(season: string): string {
    if (season === 'HIGH') {
      return 'Visoka sezona';
    }

    if (season === 'LOW') {
      return 'Niska sezona';
    }

    return 'Vansezona';
  }

  getStatusLabel(status: string): string {
    return status === 'ACTIVE' ? 'Aktivan' : 'Zaključan';
  }

  showSuccess(message: string): void {
    this.successMessage = message;
    this.errorMessage = '';

    setTimeout(() => {
      this.successMessage = '';
    }, 3000);
  }

  showError(message: string): void {
    this.errorMessage = message;
    this.successMessage = '';

    setTimeout(() => {
      this.errorMessage = '';
    }, 3000);
  }
}