import {Component, OnInit} from '@angular/core';
import {LoginApiService} from "../login-api/login-api.service";

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
    this.loginService.login(this.user.email, "")
  }

}
