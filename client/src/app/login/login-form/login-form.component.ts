import {Component, OnInit} from '@angular/core';
import {LoginApiService} from "../login-api/login-api.service";
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'asm-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {


  user: any = {};

  constructor(private loginService: LoginApiService) {
  }

  ngOnInit() {
  }

  login() {

    let loginOp: Observable<any[]> =
      this.loginService
        .login(this.user.email, "");

    loginOp.subscribe(
      comments => {
      },
      err => {
        // Log errors if any
        console.log(err);
      });
  }
}
