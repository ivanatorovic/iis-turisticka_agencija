import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';
import {
  WorkflowPhaseName,
  WorkflowService,
} from '../../../core/services/workflow';

@Component({
  selector: 'app-workflow-create',
  imports: [CommonModule, FormsModule, SidebarMenu],
  templateUrl: './workflow-create.html',
  styleUrl: './workflow-create.css',
})
export class WorkflowCreate {
  step: 'name' | 'builder' = 'name';

  workflowName = '';
  selectedPhases: WorkflowPhaseName[] = [];
  menuOpen = false;
  phaseMenuStep: 'type' | 'recommended' = 'type';
  successMessage = '';
  errorMessage = '';

  selectedPhaseForDetails: WorkflowPhaseName | null = null;

phaseTemplates: Record<WorkflowPhaseName, { label: string; fields: string[] }> = {
  DESTINATION: {
    label: 'Destinacija',
    fields: ['Država', 'Grad'],
  },
  ACCOMMODATION: {
    label: 'Smeštaj',
    fields: [
      'Tip smeštaja',
      'Naziv',
      'Lokacija',
      'Tip sobe',
      'Check in',
      'Check out',
      'Dodatne aktivnosti',
    ],
  },
  TRANSPORT: {
    label: 'Prevoz',
    fields: [
      'Tip prevoza',
      'Kompanija',
      'Polazak',
      'Odlazak',
    ],
  },
};

  recommendedPhases: { name: WorkflowPhaseName; label: string; icon: string }[] = [
    { name: 'DESTINATION', label: 'Destinacija', icon: '📍' },
    { name: 'ACCOMMODATION', label: 'Smeštaj', icon: '🏨' },
    { name: 'TRANSPORT', label: 'Prevoz', icon: '🚌' },
  ];

  constructor(private workflowService: WorkflowService) {}

  continueToBuilder(): void {
    if (!this.workflowName.trim()) {
      alert('Unesi naziv radnog toka');
      return;
    }

    this.step = 'builder';
  }

  togglePhaseMenu(): void {
  this.menuOpen = !this.menuOpen;
  this.phaseMenuStep = 'type';
}

showRecommendedPhases(): void {
  this.phaseMenuStep = 'recommended';
}

showNewPhaseOption(): void {
  this.menuOpen = false;
}

addPhase(phase: WorkflowPhaseName): void {
  if (!this.selectedPhases.includes(phase)) {
    this.selectedPhases.push(phase);
  }

  this.selectedPhaseForDetails = phase;
  this.menuOpen = false;
  this.phaseMenuStep = 'type';
}



  getPhaseLabel(phase: WorkflowPhaseName): string {
    return this.recommendedPhases.find(p => p.name === phase)?.label || phase;
  }

  getPhaseIcon(phase: WorkflowPhaseName): string {
    return this.recommendedPhases.find(p => p.name === phase)?.icon || '●';
  }

  saveWorkflow(): void {
  this.workflowService.create({
    name: this.workflowName,
    phases: this.selectedPhases,
  }).subscribe({
    next: () => {
      this.successMessage = 'Radni tok je uspešno sačuvan.';
      this.errorMessage = '';

      this.workflowName = '';
      this.selectedPhases = [];
      this.selectedPhaseForDetails = null;
      this.step = 'name';

      setTimeout(() => {
        this.successMessage = '';
      }, 3000);
    },
    error: (err) => {
      console.error(err);

      this.errorMessage = 'Greška pri čuvanju radnog toka.';
      this.successMessage = '';

      setTimeout(() => {
        this.errorMessage = '';
      }, 3000);
    },
  });
}

  selectPhaseForDetails(phase: WorkflowPhaseName): void {
  this.selectedPhaseForDetails = phase;
}

removePhase(phase: WorkflowPhaseName): void {
  this.selectedPhases = this.selectedPhases.filter(p => p !== phase);

  if (this.selectedPhaseForDetails === phase) {
    this.selectedPhaseForDetails = this.selectedPhases.length > 0
      ? this.selectedPhases[0]
      : null;
  }
}
}