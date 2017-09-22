import {NgModule} from '@angular/core';
import {
  MdButtonModule,
  MdCheckboxModule,
  MdIconModule,
  MdInputModule,
  MdMenuModule,
  MdToolbarModule
} from '@angular/material';

@NgModule({
  imports: [
    MdButtonModule,
    MdCheckboxModule,
    MdInputModule,
    MdToolbarModule,
    MdIconModule,
    MdMenuModule
  ],
  exports: [
    MdButtonModule,
    MdCheckboxModule,
    MdInputModule,
    MdToolbarModule,
    MdIconModule,
    MdMenuModule
  ],
  declarations: []
})
export class NgMaterialModule {
}
