import {Injectable} from '@angular/core';
import {HttpClient} from '../../http/http-client/http-client';
import {CreateRequest} from './request/create-request';
import {CreateResponse} from './response/create-response';
import {Observable} from 'rxjs/Observable';
import {Response} from '@angular/http';
import {GroupListRequest} from './request/group-list-request';
import {GroupListResponse} from './response/group-list-response';

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

    list(listRequest: GroupListRequest): Observable<GroupListResponse> {
        return this.http.post('/api/group/list', JSON.stringify(listRequest))
            .map((res: Response) => {
                const json = res.json();
                return new GroupListResponse(json.list);
            })
            .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    }

    // create(createRequest: LoginRequest): Observable<UserAuthenticationAttempt> {
    //
    //     return this.http.post('/api/auth/user/authenticate', JSON.stringify(loginRequest))
    //         .map((res: Response) => {
    //             const response = res.json();
    //             // add messaging later to response for why a login failed, for now we will just pass the returned or null user
    //             // to handle if it worked
    //             if (response.success) {
    // return new UserAuthenticationAttempt(response.success, new AssembleUser(response.user.email, response.user.id, response.success));
    //             } else {
    //                 return new UserAuthenticationAttempt(response.success, AssembleUser.noUser());
    //             }
    //         })
    //         .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    // }

}
