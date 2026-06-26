import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdditionalActivityAnalytics } from './additional-activity-analytics';

describe('AdditionalActivityAnalytics', () => {
  let component: AdditionalActivityAnalytics;
  let fixture: ComponentFixture<AdditionalActivityAnalytics>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdditionalActivityAnalytics]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdditionalActivityAnalytics);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
