import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecommendedActivities } from './recommended-activities';

describe('RecommendedActivities', () => {
  let component: RecommendedActivities;
  let fixture: ComponentFixture<RecommendedActivities>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RecommendedActivities]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecommendedActivities);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
