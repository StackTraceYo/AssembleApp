import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardMainComponent} from './dashboard-main/dashboard-main.component';
import {DashboardMyGroupsComponent} from './dashboard-my-groups/dashboard-my-groups.component';
import {NgMaterialModule} from '../ng-material/ng-material.module';

@NgModule({
    imports: [
        CommonModule,
        NgMaterialModule
    ],
    declarations: [DashboardMainComponent, DashboardMyGroupsComponent]
})
export class DashboardModule {
}
