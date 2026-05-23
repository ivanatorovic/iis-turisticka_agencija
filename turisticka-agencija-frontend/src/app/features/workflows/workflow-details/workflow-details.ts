import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {
  Workflow,
  WorkflowPhaseName,
  WorkflowService,
} from '../../../core/services/workflow';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';

@Component({
  selector: 'app-workflow-details',
  imports: [CommonModule, SidebarMenu],
  templateUrl: './workflow-details.html',
  styleUrl: './workflow-details.css',
})
export class WorkflowDetails implements OnInit {

  workflow?: Workflow;

  recommendedPhases: {
    name: WorkflowPhaseName;
    label: string;
    icon: string;
  }[] = [
    { name: 'DESTINATION', label: 'Destinacija', icon: '📍' },
    { name: 'ACCOMMODATION', label: 'Smeštaj', icon: '🏨' },
    { name: 'TRANSPORT', label: 'Prevoz', icon: '🚌' },
  ];

  constructor(
    private route: ActivatedRoute,
    private workflowService: WorkflowService
  ) {}

  ngOnInit(): void {

    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.workflowService.getById(id).subscribe({
      next: (data) => {
        this.workflow = data;
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  getPhaseLabel(phase: WorkflowPhaseName): string {
    return this.recommendedPhases.find(
      p => p.name === phase
    )?.label || phase;
  }

  getPhaseIcon(phase: WorkflowPhaseName): string {
    return this.recommendedPhases.find(
      p => p.name === phase
    )?.icon || '●';
  }

  selectedPhaseForDetails: WorkflowPhaseName | null = null;

phaseTemplates: Record<WorkflowPhaseName, { fields: string[] }> = {
  DESTINATION: {
    fields: ['Država', 'Grad'],
  },
  ACCOMMODATION: {
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
    fields: [
      'Tip prevoza',
      'Kompanija',
      'Polazak',
      'Odlazak',
    ],
  },
};

selectPhase(phase: WorkflowPhaseName): void {
  this.selectedPhaseForDetails = phase;
}
}