import {NgModule} from '@angular/core';
import {
    MatButtonModule,
    MatCheckboxModule,
    MatDialogModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatMenuModule,
    MatProgressSpinnerModule,
    MatStepperModule,
    MatToolbarModule,
    MatCardModule,
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
        MatStepperModule,
        MatDialogModule,
        MatCardModule
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
        MatStepperModule,
        MatDialogModule,
        MatCardModule
    ],
    declarations: []
})
export class NgMaterialModule {
}
