import {Injectable} from '@angular/core';
import {HttpClient} from '../../http/http-client/http-client';

@Injectable()
export class GroupApiService {

    constructor(private http: HttpClient) {
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
