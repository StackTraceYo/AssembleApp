import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import 'rxjs/add/operator/map'

@Injectable()
export class LoginApiService {

  constructor(private http: Http) {
  }

  login(email: string, password: string) {
    return this.http.post('localhost:9000/api/auth/user/authenticate', JSON.stringify({email: email}))
      .map((response: Response) => {
        // login successful if there's a jwt token in the response
        let user = response.json();
        if (user && user.success) {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          localStorage.setItem('currentUser', JSON.stringify(user));
        }
      });
  }

}
