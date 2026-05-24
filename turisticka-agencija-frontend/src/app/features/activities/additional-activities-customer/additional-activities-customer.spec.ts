import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdditionalActivitiesCustomer } from './additional-activities-customer';

describe('AdditionalActivitiesCustomer', () => {
  let component: AdditionalActivitiesCustomer;
  let fixture: ComponentFixture<AdditionalActivitiesCustomer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdditionalActivitiesCustomer]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdditionalActivitiesCustomer);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
