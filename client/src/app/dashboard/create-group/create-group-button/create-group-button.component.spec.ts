import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CreateGroupButtonComponent} from './create-group-button.component';

describe('CreateGroupButtonComponent', () => {
    let component: CreateGroupButtonComponent;
    let fixture: ComponentFixture<CreateGroupButtonComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [CreateGroupButtonComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(CreateGroupButtonComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
