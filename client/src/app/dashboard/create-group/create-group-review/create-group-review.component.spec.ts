import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateGroupReviewComponent } from './create-group-review.component';

describe('CreateGroupReviewComponent', () => {
  let component: CreateGroupReviewComponent;
  let fixture: ComponentFixture<CreateGroupReviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateGroupReviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateGroupReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
