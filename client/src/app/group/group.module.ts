import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GroupApiService} from './group-api/group-api.service';
import {HttpClientModule} from '../http/http.client.module';

@NgModule({
    imports: [
        CommonModule,
        HttpClientModule
    ],
    declarations: [],
    providers: [GroupApiService]
})
export class GroupModule {
}
