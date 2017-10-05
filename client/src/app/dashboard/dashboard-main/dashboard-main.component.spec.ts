import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DashboardMainComponent} from './dashboard-main.component';
import {DashboardMyGroupsComponent} from '../dashboard-my-groups/dashboard-my-groups.component';
import {NgMaterialModule} from '../../ng-material/ng-material.module';

describe('DashboardMainComponent', () => {
    let component: DashboardMainComponent;
    let fixture: ComponentFixture<DashboardMainComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [NgMaterialModule],
            declarations: [DashboardMainComponent, DashboardMyGroupsComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DashboardMainComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
