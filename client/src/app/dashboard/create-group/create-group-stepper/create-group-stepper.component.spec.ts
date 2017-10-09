import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateGroupStepperComponent } from './create-group-stepper.component';

describe('CreateGroupStepperComponent', () => {
  let component: CreateGroupStepperComponent;
  let fixture: ComponentFixture<CreateGroupStepperComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateGroupStepperComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateGroupStepperComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
