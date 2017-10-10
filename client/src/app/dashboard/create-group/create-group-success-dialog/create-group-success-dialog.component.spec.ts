import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateGroupSuccessDialogComponent } from './create-group-success-dialog.component';

describe('CreateGroupSuccessDialogComponent', () => {
  let component: CreateGroupSuccessDialogComponent;
  let fixture: ComponentFixture<CreateGroupSuccessDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateGroupSuccessDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateGroupSuccessDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
