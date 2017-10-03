import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardMyGroupsComponent } from './dashboard-my-groups.component';

describe('DashboardMyGroupsComponent', () => {
  let component: DashboardMyGroupsComponent;
  let fixture: ComponentFixture<DashboardMyGroupsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DashboardMyGroupsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardMyGroupsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
