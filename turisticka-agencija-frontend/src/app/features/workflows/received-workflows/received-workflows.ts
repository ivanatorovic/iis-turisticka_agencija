import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';
import { Workflow, WorkflowService } from '../../../core/services/workflow';

@Component({
  selector: 'app-received-workflows',
  imports: [CommonModule, SidebarMenu, RouterLink],
  templateUrl: './received-workflows.html',
  styleUrl: './received-workflows.css',
})
export class ReceivedWorkflows implements OnInit {
  workflows: Workflow[] = [];

  constructor(private workflowService: WorkflowService) {}

  ngOnInit(): void {
    this.workflowService.getReceivedWorkflows().subscribe({
      next: (data) => {
        this.workflows = data;
      },
      error: (err) => {
        console.error(err);
      },
    });
  }
}