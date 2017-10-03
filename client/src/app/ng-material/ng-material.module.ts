import {NgModule} from '@angular/core';
import {
    MdButtonModule,
    MdCheckboxModule, MdGridListModule,
    MdIconModule,
    MdInputModule,
    MdMenuModule, MdProgressSpinnerModule,
    MdToolbarModule
} from '@angular/material';

@NgModule({
    imports: [
        MdButtonModule,
        MdCheckboxModule,
        MdInputModule,
        MdToolbarModule,
        MdIconModule,
        MdMenuModule,
        MdProgressSpinnerModule,
        MdGridListModule
    ],
    exports: [
        MdButtonModule,
        MdCheckboxModule,
        MdInputModule,
        MdToolbarModule,
        MdIconModule,
        MdMenuModule,
        MdProgressSpinnerModule,
        MdGridListModule
    ],
    declarations: []
})
export class NgMaterialModule {
}
