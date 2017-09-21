import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HttpClient} from "./http-client/http-client";

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [],
  providers: [HttpClient]
})
export class HttpClientModule {
}
