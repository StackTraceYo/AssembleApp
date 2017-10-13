import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ContentService} from './content-api/content.service';
import {HttpClientModule} from '../http/http.client.module';

@NgModule({
    imports: [
        CommonModule,
        HttpClientModule
    ],
    declarations: [],
    providers: [ContentService]
})
export class ContentModule {
}
