import {Injectable} from '@angular/core';
import {Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {HttpClient} from "../../http/http-client/http-client";


@Injectable()
export class LoginApiService {

  constructor(private http: HttpClient) {
  }

  login(email: string, password: string) {

    return this.http.post('/api/auth/user/authenticate', JSON.stringify({email: email}))
      .map((res: Response) => res.json())
      .catch((error: any) => Observable.throw(error.json().error || 'Server error'));

  }

}
