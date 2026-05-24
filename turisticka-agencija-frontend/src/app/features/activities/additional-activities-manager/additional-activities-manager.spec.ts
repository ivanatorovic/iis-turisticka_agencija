import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdditionalActivitiesManager } from './additional-activities-manager';

describe('AdditionalActivitiesManager', () => {
  let component: AdditionalActivitiesManager;
  let fixture: ComponentFixture<AdditionalActivitiesManager>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdditionalActivitiesManager]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdditionalActivitiesManager);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
