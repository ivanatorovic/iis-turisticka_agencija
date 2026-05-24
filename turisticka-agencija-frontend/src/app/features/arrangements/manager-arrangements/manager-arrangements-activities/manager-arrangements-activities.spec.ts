import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerArrangementsActivities } from './manager-arrangements-activities';

describe('ManagerArrangementsActivities', () => {
  let component: ManagerArrangementsActivities;
  let fixture: ComponentFixture<ManagerArrangementsActivities>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManagerArrangementsActivities]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManagerArrangementsActivities);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
