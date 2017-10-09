import {NgModule} from '@angular/core';
import {
    MatButtonModule,
    MatCheckboxModule, MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatMenuModule, MatProgressSpinnerModule,
    MatToolbarModule,
    MatStepperModule
} from '@angular/material';

@NgModule({
    imports: [
        MatButtonModule,
        MatCheckboxModule,
        MatInputModule,
        MatToolbarModule,
        MatIconModule,
        MatMenuModule,
        MatProgressSpinnerModule,
        MatGridListModule,
        MatStepperModule
    ],
    exports: [
        MatButtonModule,
        MatCheckboxModule,
        MatInputModule,
        MatToolbarModule,
        MatIconModule,
        MatMenuModule,
        MatProgressSpinnerModule,
        MatGridListModule,
        MatStepperModule
    ],
    declarations: []
})
export class NgMaterialModule {
}
