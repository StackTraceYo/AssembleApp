import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginFormComponent } from './login-form/login-form.component';
import {NgMaterialModule} from "../ng-material/ng-material.module";

@NgModule({
  imports: [
    CommonModule,
    NgMaterialModule
  ],
  declarations: [LoginFormComponent]
})
export class LoginModule { }
