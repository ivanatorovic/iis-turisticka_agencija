import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  ActivatedRoute,
  Router
} from '@angular/router';
import {
  HttpClient,
  HttpHeaders,
} from '@angular/common/http';

import {
  ManagerArrangementService,
  ManagerArrangementRequest,
} from '../../../../core/services/manager-arrangement';

import { WorkflowService } from '../../../../core/services/workflow';
import { DestinationService } from '../../../../core/services/destination';

import { SidebarMenu } from '../../../../layout/sidebar-menu/sidebar-menu';

@Component({
  selector: 'app-manager-arrangements-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    SidebarMenu,
  ],
  templateUrl: './manager-arrangements-form.html',
  styleUrl: './manager-arrangements-form.css',
})
export class ManagerArrangementForm implements OnInit {
  workflowId!: number;
  arrangementId!: number;

  isEditMode = false;

  workflows: any[] = [];
  destinations: any[] = [];
  accommodations: any[] = [];
  transports: any[] = [];
  workflow: any = null;
  countries: string[] = [];
filteredDestinations: any[] = [];
selectedCountry: string | null = null;

  successMessage = '';
  errorMessage = '';

  arrangement: ManagerArrangementRequest = {
    name: '',
    description: '',
    basePrice: null,
    numberOfNights: null,
    imageUrl: '',
    workflowId: 0,
    managerId: 0,
    destinationId: null,
    accommodationId: null,
    transportId: null,
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private managerArrangementService: ManagerArrangementService,
    private workflowService: WorkflowService,
    private destinationService: DestinationService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.workflowId = Number(
      this.route.snapshot.paramMap.get('workflowId')
    );

    const arrangementIdParam =
      this.route.snapshot.paramMap.get('arrangementId');

    if (arrangementIdParam) {
      this.arrangementId = Number(arrangementIdParam);
      this.isEditMode = true;
    }

    this.arrangement.workflowId = this.workflowId;
    this.arrangement.managerId = Number(
      localStorage.getItem('userId')
    );
    this.loadWorkflow();
    this.loadDestinations();
    this.loadAccommodations();
    this.loadTransports();

    if (this.isEditMode) {
      this.loadArrangement();
    }
  }

  private getHeaders() {
    const token = localStorage.getItem('token');

    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`,
      }),
    };
  }

  loadArrangement(): void {
    this.managerArrangementService
      .getById(this.arrangementId)
      .subscribe({
        next: (data: any) => {
          this.arrangement = {
            name: data.name,
            description: data.description,
            basePrice: data.basePrice,
            numberOfNights: data.numberOfNights,
            imageUrl: data.imageUrl,
            workflowId: data.workflow.id,
            managerId: data.manager.id,
            destinationId: data.destination?.id || null,
            accommodationId:
              data.accommodation?.id || null,
            transportId: data.transport?.id || null,
          };
        },
        error: () => {
          this.errorMessage =
            'Greška pri učitavanju aranžmana.';
        },
      });
  }

  loadDestinations(): void {
  this.destinationService.getAll().subscribe({
    next: (data: any[]) => {
      this.destinations = data;

      this.countries = [
        ...new Set(data.map(destination => destination.country))
      ];
    },
    error: () => {
      this.errorMessage = 'Greška pri učitavanju destinacija.';
    },
  });
}
  loadAccommodations(): void {
    this.http
      .get<any[]>(
        'http://localhost:8080/api/accommodations',
        this.getHeaders()
      )
      .subscribe({
        next: (data: any) => {
          this.accommodations = data;
        },
        error: () => {
          this.errorMessage =
            'Greška pri učitavanju smeštaja.';
        },
      });
  }

  loadTransports(): void {
    this.http
      .get<any[]>(
        'http://localhost:8080/api/transports',
        this.getHeaders()
      )
      .subscribe({
        next: (data: any) => {
          this.transports = data;
        },
        error: () => {
          this.errorMessage =
            'Greška pri učitavanju transporta.';
        },
      });
  }

  hasPhase(phaseType: string): boolean {
  if (!this.workflow || !this.workflow.phases) {
    return false;
  }

  return this.workflow.phases.some((phase: any) => {
    return phase.name === phaseType || phase.type === phaseType;
  });
}

isFormValid(): boolean {
  if (!this.arrangement.name.trim()) {
    return false;
  }

  if (!this.arrangement.description.trim()) {
    return false;
  }

  if (
    this.arrangement.basePrice === null ||
    this.arrangement.basePrice <= 0
  ) {
    return false;
  }

  if (
    this.arrangement.numberOfNights === null ||
    this.arrangement.numberOfNights <= 0
  ) {
    return false;
  }

  if (this.hasPhase('DESTINATION') && !this.arrangement.destinationId) {
    return false;
  }

  if (this.hasPhase('ACCOMMODATION') && !this.arrangement.accommodationId) {
    return false;
  }

  if (this.hasPhase('TRANSPORT') && !this.arrangement.transportId) {
    return false;
  }

  return true;
}

  saveArrangement(): void {
    if (this.isEditMode) {
      this.managerArrangementService
        .update(this.arrangementId, this.arrangement)
        .subscribe({
          next: () => {
            this.successMessage =
              'Aranžman je uspešno izmenjen.';

            setTimeout(() => {
              this.router.navigate([
                '/my-manager-arrangements',
              ]);
            }, 1000);
          },
          error: () => {
            this.errorMessage =
              'Greška pri izmeni aranžmana.';
          },
        });
    } else {
      this.managerArrangementService
        .create(this.arrangement)
        .subscribe({
          next: () => {
            this.successMessage =
              'Aranžman je uspešno kreiran.';

            setTimeout(() => {
              this.router.navigate([
                '/my-manager-arrangements',
              ]);
            }, 1000);
          },
          error: () => {
            this.errorMessage =
              'Greška pri kreiranju aranžmana.';
          },
        });
    }
  }

  loadWorkflow(): void {
  this.workflowService.getById(this.workflowId).subscribe({
    next: (data: any) => {
      this.workflow = data;
    },
    error: () => {
      this.errorMessage = 'Greška pri učitavanju radnog toka.';
    },
  });
}

onCountryChange(): void {
  this.arrangement.destinationId = null;

  this.filteredDestinations = this.destinations.filter(
    destination => destination.country === this.selectedCountry
  );
}
}