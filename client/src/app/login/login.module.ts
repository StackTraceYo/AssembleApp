import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginFormComponent } from './login-form/login-form.component';
import {NgMaterialModule} from "../ng-material/ng-material.module";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

@NgModule({
  imports: [
    CommonModule,
    NgMaterialModule,
    FormsModule,
    HttpModule
  ],
  declarations: [LoginFormComponent]
})
export class LoginModule { }
