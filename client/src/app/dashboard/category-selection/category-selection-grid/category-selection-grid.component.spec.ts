import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {CategorySelectionGridComponent} from './category-selection-grid.component';



describe('CategorySelectionComponent', () => {
  let component: CategorySelectionGridComponent;
  let fixture: ComponentFixture<CategorySelectionGridComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CategorySelectionGridComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CategorySelectionGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
