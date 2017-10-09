import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardMainComponent} from './dashboard-main/dashboard-main.component';
import {DashboardMyGroupsComponent} from './dashboard-my-groups/dashboard-my-groups.component';
import {NgMaterialModule} from '../ng-material/ng-material.module';
import {JoinGroupComponent} from './join-group/join-group.component';
import {CreateGroupButtonComponent} from './create-group/create-group-button/create-group-button.component';

@NgModule({
    imports: [
        CommonModule,
        NgMaterialModule
    ],
    declarations: [DashboardMainComponent, DashboardMyGroupsComponent, CreateGroupButtonComponent, JoinGroupComponent]
})
export class DashboardModule {
}
