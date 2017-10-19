import {Injectable} from '@angular/core';
import {Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {HttpClient} from '../../http/http-client/http-client';
import {RegisterRequest} from './request/register-request';
import {LoginRequest} from './request/login-request';
import {UserAuthenticationAttempt} from './model/user-authentication-attempt';
import {AssembleUser} from '../assemble.user';


@Injectable()
export class UserApiService {

    constructor(private http: HttpClient) {
    }


    static extractHeader(res: Response): string {
        return res.headers.get('X-Asm-Auth');
    }

    login(loginRequest: LoginRequest) {

        return this.http.post('/api/auth/user/authenticate', JSON.stringify(loginRequest));
    }

    register(registerRequest: RegisterRequest): Observable<UserAuthenticationAttempt> {
        return this.http.post('/api/auth/user/create', JSON.stringify(registerRequest))
            .map((res: Response) => {
                const response = res.json();
                const token = UserApiService.extractHeader(res);
                return new UserAuthenticationAttempt(response.success,
                    new AssembleUser(registerRequest.email, response.id, response.success),
                    token
                );
            })
            .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    }


}
