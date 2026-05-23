import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';

import {
  DestinationCalendar,
  DestinationCalendarService
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
  standalone: true,
  imports: [CommonModule, FormsModule, NgIf, NgFor, SidebarMenu],
  templateUrl: './manager-calendars.html',
  styleUrls: ['./manager-calendars.css']
})
export class ManagerCalendarsComponent implements OnInit {
  calendars: DestinationCalendar[] = [];
  destinations: Destination[] = [];

  successMessage = '';
  errorMessage = '';

  editingCalendarId: number | null = null;

  newCalendar = {
    name: '',
    destinationId: null as number | null,
    startDate: '',
    endDate: ''
  };

  editCalendar = {
    name: '',
    destinationId: null as number | null,
    startDate: '',
    endDate: ''
  };

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
      next: (data) => {
        this.calendars = data;
      },
      error: () => {
        this.errorMessage = 'Greška pri učitavanju kalendara.';
      }
    });
  }

  loadDestinations(): void {
    this.destinationService.getAll().subscribe({
      next: (data) => {
        this.destinations = data;
      },
      error: () => {
        this.errorMessage = 'Greška pri učitavanju destinacija.';
      }
    });
  }

  addCalendar(): void {
    if (
      !this.newCalendar.name ||
      !this.newCalendar.destinationId ||
      !this.newCalendar.startDate ||
      !this.newCalendar.endDate
    ) {
      this.errorMessage = 'Popuni sva polja.';
      return;
    }

    const calendar = {
      name: this.newCalendar.name,
      startDate: this.newCalendar.startDate,
      endDate: this.newCalendar.endDate,
      destination: {
        id: this.newCalendar.destinationId
      }
    };

    this.calendarService.create(calendar).subscribe({
      next: () => {
        this.successMessage = 'Kalendar je uspešno dodat.';
        this.errorMessage = '';

        this.newCalendar = {
          name: '',
          destinationId: null,
          startDate: '',
          endDate: ''
        };

        this.loadCalendars();
      },
      error: () => {
        this.errorMessage = 'Greška pri dodavanju kalendara.';
        this.successMessage = '';
      }
    });
  }

  startEdit(calendar: DestinationCalendar): void {
    this.editingCalendarId = calendar.id;

    this.editCalendar = {
      name: calendar.name,
      destinationId: calendar.destination.id,
      startDate: calendar.startDate,
      endDate: calendar.endDate
    };
  }

  saveEdit(id: number): void {
    if (
      !this.editCalendar.name ||
      !this.editCalendar.destinationId ||
      !this.editCalendar.startDate ||
      !this.editCalendar.endDate
    ) {
      this.errorMessage = 'Popuni sva polja.';
      return;
    }

    const updates = {
      name: this.editCalendar.name,
      startDate: this.editCalendar.startDate,
      endDate: this.editCalendar.endDate,
      destination: {
        id: this.editCalendar.destinationId
      }
    };

    this.calendarService.patch(id, updates).subscribe({
      next: () => {
        this.successMessage = 'Kalendar je uspešno izmenjen.';
        this.errorMessage = '';
        this.editingCalendarId = null;
        this.loadCalendars();
      },
      error: () => {
        this.errorMessage = 'Greška pri izmeni kalendara.';
        this.successMessage = '';
      }
    });
  }

  cancelEdit(): void {
    this.editingCalendarId = null;
  }

  deleteCalendar(id: number): void {
    this.calendarService.delete(id).subscribe({
      next: () => {
        this.successMessage = 'Kalendar je obrisan.';
        this.errorMessage = '';
        this.loadCalendars();
      },
      error: () => {
        this.errorMessage = 'Greška pri brisanju kalendara.';
        this.successMessage = '';
      }
    });
  }
}