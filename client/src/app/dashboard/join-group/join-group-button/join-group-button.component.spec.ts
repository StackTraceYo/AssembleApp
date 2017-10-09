import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {JoinGroupComponent} from './join-group-button.component';

describe('JoinGroupComponent', () => {
    let component: JoinGroupComponent;
    let fixture: ComponentFixture<JoinGroupComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [JoinGroupComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(JoinGroupComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
