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
import {ContentModule} from '../content/content.module';
import {CategorySelectionGridComponent} from './category-selection/category-selection-grid/category-selection-grid.component';
import {CreateGroupReviewComponent} from './create-group/create-group-review/create-group-review.component';
import {reducers} from './reducers/reducers';
import {StoreModule} from '@ngrx/store';
import {EffectsModule} from '@ngrx/effects';
import {NgrxFormsModule} from 'ngrx-forms';
import {DashGroupEffects} from './effects/dash.group.effects';

@NgModule({
    imports: [
        CommonModule,
        NgMaterialModule,
        FormsModule,
        ReactiveFormsModule,
        GroupModule,
        AppRoutingModule,
        ContentModule,
        NgrxFormsModule,
        StoreModule,
        StoreModule.forFeature('dash', reducers),
        EffectsModule.forFeature([DashGroupEffects])
    ],
    declarations: [
        DashboardMainComponent,
        DashboardMyGroupsComponent,
        CreateGroupButtonComponent,
        JoinGroupComponent,
        CreateGroupStepperComponent,
        CreateGroupSuccessDialogComponent,
        CategorySelectionGridComponent,
        CreateGroupReviewComponent
    ],
    entryComponents: [CreateGroupSuccessDialogComponent]

})
export class DashboardModule {
}
