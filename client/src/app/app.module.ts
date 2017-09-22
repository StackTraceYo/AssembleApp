import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {AppComponent} from './app.component';
import {AppRoutingModule} from "./app-routing/app-routing.module";
import {UserModule} from "./user-auth/user.module";
import {NgMaterialModule} from "./ng-material/ng-material.module";
import {HttpModule} from "@angular/http";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "./http/http.client.module";
import {HttpClient} from "./http/http-client/http-client";
import {DashboardModule} from "./dashboard/dashboard.module";
import {ToolbarComponent} from "./toolbar/toolbar.component";
import {AuthGuard} from "./user-auth/auth-guard/auth.guard";

@NgModule({
  declarations: [
    AppComponent, ToolbarComponent
  ],
  imports: [
    NgMaterialModule,
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    HttpModule,
    HttpClientModule,
    AppRoutingModule,
    UserModule,
    DashboardModule,
  ],
  providers: [HttpClient, AuthGuard],
  bootstrap: [AppComponent],
})
export class AppModule {
}
