import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginFormComponent} from './login-form/login-form.component';
import {NgMaterialModule} from "../ng-material/ng-material.module";
import {FormsModule} from "@angular/forms";
import {UserApiService} from "./user-api/user-api.service";
import {RegisterFormComponent} from './register-form/register-form.component';
import {RouterModule} from "@angular/router";

@NgModule({
  imports: [
    CommonModule,
    NgMaterialModule,
    FormsModule,
    RouterModule
  ],
  declarations: [LoginFormComponent, RegisterFormComponent],
  providers: [UserApiService]
})
export class UserModule {
}
