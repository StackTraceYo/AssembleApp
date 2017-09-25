import {NgModule} from '@angular/core';
import {
  MdButtonModule,
  MdCheckboxModule,
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
    MdProgressSpinnerModule
  ],
  exports: [
    MdButtonModule,
    MdCheckboxModule,
    MdInputModule,
    MdToolbarModule,
    MdIconModule,
    MdMenuModule,
    MdProgressSpinnerModule
  ],
  declarations: []
})
export class NgMaterialModule {
}
