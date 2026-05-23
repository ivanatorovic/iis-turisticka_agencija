import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth';
import { Observable } from 'rxjs';

export type WorkflowPhaseName = 'DESTINATION' | 'ACCOMMODATION' | 'TRANSPORT';

export interface WorkflowPhase {
  id: number;
  name: WorkflowPhaseName;
  type: 'RECOMMENDED' | 'NEW';
}

export interface Workflow {
  id: number;
  name: string;
  createdAt: string;
  phases: WorkflowPhase[];
  sentToManager?: boolean;
managerUsername?: string;
}

export interface CreateWorkflowRequest {
  name: string;
  phases: WorkflowPhaseName[];
}

@Injectable({
  providedIn: 'root',
})
export class WorkflowService {
  private apiUrl = 'http://localhost:8080/api/workflows';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      Authorization: `Bearer ${this.authService.getToken()}`,
    });
  }

  create(request: CreateWorkflowRequest): Observable<Workflow> {
    return this.http.post<Workflow>(this.apiUrl, request, {
      headers: this.getHeaders(),
    });
  }

  getAll(): Observable<Workflow[]> {
    return this.http.get<Workflow[]>(this.apiUrl, {
      headers: this.getHeaders(),
    });
  }

  getMyWorkflows(): Observable<Workflow[]> {
    return this.http.get<Workflow[]>(`${this.apiUrl}/my`, {
      headers: this.getHeaders(),
    });
  }

  deleteWorkflow(id: number): Observable<string> {
    return this.http.delete(`${this.apiUrl}/${id}`, {
      headers: this.getHeaders(),
      responseType: 'text',
    });
  }

  deletePhase(workflowId: number, phaseId: number): Observable<Workflow> {
    return this.http.delete<Workflow>(
      `${this.apiUrl}/${workflowId}/phases/${phaseId}`,
      { headers: this.getHeaders() }
    );
  }

  getById(id: number): Observable<Workflow> {
  return this.http.get<Workflow>(
    `${this.apiUrl}/${id}`,
    {
      headers: this.getHeaders(),
    }
  );
}

sendToManager(id: number, managerUsername: string): Observable<Workflow> {
  return this.http.put<Workflow>(
    `${this.apiUrl}/${id}/send-to-manager`,
    { managerUsername },
    { headers: this.getHeaders() }
  );
}

getReceivedWorkflows(): Observable<Workflow[]> {
  return this.http.get<Workflow[]>(`${this.apiUrl}/received`, {
    headers: this.getHeaders(),
  });
}
}