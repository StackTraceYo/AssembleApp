import {Injectable} from '@angular/core';
import {HttpClient} from '../../http/http-client/http-client';
import {CreateRequest} from './request/create-request';
import {CreateResponse} from './response/create-response';
import {Observable} from 'rxjs/Observable';
import {Response} from '@angular/http';
import {GroupListRequest} from './request/group-list-request';

@Injectable()
export class GroupApiService {

    constructor(private http: HttpClient) {
    }

    create(createRequest: CreateRequest): Observable<CreateResponse> {
        return this.http.post('/api/group/create', JSON.stringify(createRequest))
            .map((res: Response) => {
                const json = res.json();
                return new CreateResponse(json.groupId, json.success);
            })
            .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    }

    list(listRequest: GroupListRequest, token: string = ''): any {
        return this.http.post('/api/group/list', JSON.stringify(listRequest), token);
    }
}
