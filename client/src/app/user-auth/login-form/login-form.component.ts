import {Component, OnInit} from '@angular/core';
import {UserApiService} from "../user-api/user-api.service";
import {Observable} from "rxjs/Observable";
import {Router} from '@angular/router';
import {LoginFormModel} from "./model/login-form-model";
import {LoginRequest} from "../user-api/request/login-request";
import {UserAuthenticationAttempt} from "../user-api/model/user-authentication-attempt";
import {UserService} from "../user-service";


@Component({
  selector: 'asm-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss'],
})
export class LoginFormComponent implements OnInit {


  user: LoginFormModel;
  appname: "Assemble";

  constructor(private userApiService: UserApiService, private userService: UserService, private router: Router) {
  }

  ngOnInit() {
    this.user = new LoginFormModel("","");
  }

  login() {

    let loginOp: Observable<UserAuthenticationAttempt> =
      this.userApiService
        .login(new LoginRequest(this.user));

    loginOp.subscribe(
      attempt => {
        if (attempt.authenticated) {
          //set token/cookie stuff
          this.userService.storeUser(attempt.user)
          this.goToDash();
        }
        //show error
      },
      err => {
        // Log errors if any
        console.log(err);
      });
  }

  goToRegistration = function () {
    this.router.navigateByUrl('/register');
  };

  goToDash = function () {
    this.router.navigateByUrl('/asm');
  };
}
