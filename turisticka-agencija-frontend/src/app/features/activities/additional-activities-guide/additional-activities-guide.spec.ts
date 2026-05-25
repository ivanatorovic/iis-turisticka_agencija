import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdditionalActivitiesGuide } from './additional-activities-guide';

describe('AdditionalActivitiesGuide', () => {
  let component: AdditionalActivitiesGuide;
  let fixture: ComponentFixture<AdditionalActivitiesGuide>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdditionalActivitiesGuide]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdditionalActivitiesGuide);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
