import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdditionalActivityParticipants } from './additional-activity-participants';

describe('AdditionalActivityParticipants', () => {
  let component: AdditionalActivityParticipants;
  let fixture: ComponentFixture<AdditionalActivityParticipants>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdditionalActivityParticipants]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdditionalActivityParticipants);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
