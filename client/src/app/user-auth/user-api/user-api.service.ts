import {Injectable} from '@angular/core';
import {Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {HttpClient} from "../../http/http-client/http-client";
import {RegisterRequest} from "./request/register-request";
import {LoginRequest} from "./request/login-request";
import {UserAuthenticationAttempt} from "./model/user-authentication-attempt";
import {AssembleUser} from "../assemble.user.";


@Injectable()
export class UserApiService {

  constructor(private http: HttpClient) {
  }

  login(loginRequest: LoginRequest): Observable<UserAuthenticationAttempt> {

    return this.http.post('/api/auth/user/authenticate', JSON.stringify(loginRequest))
      .map((res: Response) => {
        let response = res.json();
        //add messaging later to response for why a login failed, for now we will just pass the returned or null user
        //to handle if it worked
        return new UserAuthenticationAttempt(response.success, response.user);
      })
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

  register(registerRequest: RegisterRequest): Observable<UserAuthenticationAttempt> {
    return this.http.post('/api/auth/user/create', JSON.stringify(registerRequest))
      .map((res: Response) => {
        let response = res.json();
        return new UserAuthenticationAttempt(response.success,
          new AssembleUser(registerRequest.email, response.id, response.success)
        );
      })
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
  }

}
