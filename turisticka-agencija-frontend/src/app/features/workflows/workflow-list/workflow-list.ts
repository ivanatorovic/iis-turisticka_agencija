import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';
import { Workflow, WorkflowService } from '../../../core/services/workflow';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-workflow-list',
  imports: [CommonModule, SidebarMenu, RouterLink, FormsModule],
  templateUrl: './workflow-list.html',
  styleUrl: './workflow-list.css',
})
export class WorkflowList implements OnInit {
  workflows: Workflow[] = [];

  showDeleteModal = false;
  selectedWorkflowId: number | null = null;

  successMessage = '';
  errorMessage = '';

  showSendModal = false;
  selectedWorkflowForSend: number | null = null;
  managerUsername = '';

  constructor(private workflowService: WorkflowService) {}

  ngOnInit(): void {
    this.loadWorkflows();
  }

  loadWorkflows(): void {
    this.workflowService.getMyWorkflows().subscribe({
      next: (data) => {
        this.workflows = data;
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  openDeleteModal(id: number): void {
    this.selectedWorkflowId = id;
    this.showDeleteModal = true;
  }

  closeDeleteModal(): void {
    this.selectedWorkflowId = null;
    this.showDeleteModal = false;
  }

  confirmDeleteWorkflow(): void {
    if (this.selectedWorkflowId === null) {
      return;
    }

    this.workflowService.deleteWorkflow(this.selectedWorkflowId).subscribe({
      next: () => {
        this.loadWorkflows();
        this.closeDeleteModal();

        this.successMessage = 'Radni tok je obrisan.';
        this.errorMessage = '';

        setTimeout(() => {
          this.successMessage = '';
        }, 3000);
      },
      error: (err) => {
        console.error(err);
        this.closeDeleteModal();

        this.errorMessage = 'Greška pri brisanju radnog toka.';
        this.successMessage = '';

        setTimeout(() => {
          this.errorMessage = '';
        }, 3000);
      },
    });
  }

  openSendModal(id: number): void {
    this.selectedWorkflowForSend = id;
    this.managerUsername = '';
    this.showSendModal = true;
  }

  closeSendModal(): void {
    this.selectedWorkflowForSend = null;
    this.managerUsername = '';
    this.showSendModal = false;
  }

  confirmSendToManager(): void {
    if (this.selectedWorkflowForSend === null || !this.managerUsername.trim()) {
      return;
    }

    const workflowId = this.selectedWorkflowForSend;
    const manager = this.managerUsername.trim();

    this.workflowService.sendToManager(workflowId, manager).subscribe({
      next: () => {
        const workflow = this.workflows.find((w) => w.id === workflowId);

        if (workflow) {
          workflow.sentToManager = true;
          workflow.managerUsername = manager;
        }

        this.closeSendModal();

        this.successMessage = 'Radni tok je poslat menadžeru.';
        this.errorMessage = '';

        setTimeout(() => {
          this.successMessage = '';
        }, 3000);
      },
      error: (err) => {
        console.error(err);

        this.errorMessage = 'Greška pri slanju radnog toka.';
        this.successMessage = '';

        setTimeout(() => {
          this.errorMessage = '';
        }, 3000);
      },
    });
  }
}