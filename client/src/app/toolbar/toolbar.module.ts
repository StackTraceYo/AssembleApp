import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ToolbarComponent} from './toolbar.component';
import {NgMaterialModule} from '../ng-material/ng-material.module';
import {FormsModule} from '@angular/forms';
import {AppRoutingModule} from '../app-routing/app-routing.module';

@NgModule({
    imports: [
        CommonModule,
        NgMaterialModule,
        FormsModule,
        AppRoutingModule
    ],
    declarations: [ToolbarComponent]
})
export class ToolbarModule {
}
