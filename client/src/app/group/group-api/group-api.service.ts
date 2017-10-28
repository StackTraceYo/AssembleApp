import {Injectable} from '@angular/core';
import {HttpClient} from '../../http/http-client/http-client';
import {CreateRequest} from './request/create-request';
import {GroupListRequest} from './request/group-list-request';

@Injectable()
export class GroupApiService {

    constructor(private http: HttpClient) {
    }

    create(createRequest: CreateRequest, token: string = ''): any {
        return this.http.post('/api/group/create', JSON.stringify(createRequest), token);
    }

    list(listRequest: GroupListRequest, token: string = ''): any {
        return this.http.post('/api/group/list', JSON.stringify(listRequest), token);
    }
}
