import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkflowCreate } from './workflow-create';

describe('WorkflowCreate', () => {
  let component: WorkflowCreate;
  let fixture: ComponentFixture<WorkflowCreate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WorkflowCreate]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WorkflowCreate);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
