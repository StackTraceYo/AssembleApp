import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GroupApiService} from './group-api/group-api.service';
import {HttpClientModule} from '../http/http.client.module';
import {GroupService} from './group.service';

@NgModule({
    imports: [
        CommonModule,
        HttpClientModule
    ],
    declarations: [],
    providers: [GroupApiService, GroupService]
})
export class GroupModule {
}
