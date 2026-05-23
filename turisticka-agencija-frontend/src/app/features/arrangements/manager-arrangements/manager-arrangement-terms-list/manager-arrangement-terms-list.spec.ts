import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerArrangementTermsList } from './manager-arrangement-terms-list';

describe('ManagerArrangementTermsList', () => {
  let component: ManagerArrangementTermsList;
  let fixture: ComponentFixture<ManagerArrangementTermsList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManagerArrangementTermsList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManagerArrangementTermsList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
