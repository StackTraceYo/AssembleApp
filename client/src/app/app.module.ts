import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {AppRoutingModule} from "./app-routing/app-routing.module";
import {LoginModule} from "./login/login.module";
import {LoginApiService} from "./login/login-api/login-api.service";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LoginModule
  ],
  providers: [LoginApiService],
  bootstrap: [AppComponent]
})
export class AppModule { }
