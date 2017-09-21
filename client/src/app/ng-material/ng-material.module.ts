import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MdButtonModule, MdCheckboxModule, MdInputModule} from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    MdButtonModule,
    MdCheckboxModule,
    MdInputModule
  ],
  exports: [
    MdButtonModule,
    MdCheckboxModule,
    MdInputModule],
  declarations: []
})
export class NgMaterialModule {
}
