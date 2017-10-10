import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardMainComponent} from './dashboard-main/dashboard-main.component';
import {DashboardMyGroupsComponent} from './dashboard-my-groups/dashboard-my-groups.component';
import {NgMaterialModule} from '../ng-material/ng-material.module';
import {JoinGroupComponent} from './join-group/join-group-button/join-group-button.component';
import {CreateGroupButtonComponent} from './create-group/create-group-button/create-group-button.component';
import {CreateGroupStepperComponent} from './create-group/create-group-stepper/create-group-stepper.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {GroupModule} from '../group/group.module';
import {CreateGroupSuccessDialogComponent} from './create-group/create-group-success-dialog/create-group-success-dialog.component';
import {AppRoutingModule} from '../app-routing/app-routing.module';


@NgModule({
    imports: [
        CommonModule,
        NgMaterialModule,
        FormsModule,
        ReactiveFormsModule,
        GroupModule,
        AppRoutingModule
    ],
    declarations: [
        DashboardMainComponent,
        DashboardMyGroupsComponent,
        CreateGroupButtonComponent,
        JoinGroupComponent,
        CreateGroupStepperComponent,
        CreateGroupSuccessDialogComponent
    ],
    entryComponents: [CreateGroupSuccessDialogComponent]

})
export class DashboardModule {
}
