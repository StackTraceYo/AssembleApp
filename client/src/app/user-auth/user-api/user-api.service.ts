import {Injectable} from '@angular/core';
import {Response} from '@angular/http';
import {HttpClient} from '../../http/http-client/http-client';
import {RegisterRequest} from './request/register-request';
import {LoginRequest} from './request/login-request';


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

    register(registerRequest: RegisterRequest) {
        return this.http.post('/api/auth/user/create', JSON.stringify(registerRequest));
    }


}
