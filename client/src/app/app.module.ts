import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import {AppRoutingModule} from "./app-routing/app-routing.module";
import {LoginModule} from "./login/login.module";
import {LoginApiService} from "./login/login-api/login-api.service";
import {NgMaterialModule} from "./ng-material/ng-material.module";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    NgMaterialModule,
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    LoginModule
  ],
  providers: [LoginApiService],
  bootstrap: [AppComponent]
})
export class AppModule { }
