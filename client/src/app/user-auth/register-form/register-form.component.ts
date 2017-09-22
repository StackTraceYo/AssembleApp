import {Component, OnInit} from '@angular/core';
import {UserApiService} from "../user-api/user-api.service";
import {RegisterRequest} from "../user-api/request/register-request";
import {Observable} from "rxjs";
import {RegistrationFormModel} from "./model/registeration-form-model";
import {UserAuthenticationAttempt} from "../user-api/model/user-authentication-attempt";
import {Router} from "@angular/router";

@Component({
  selector: 'asm-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css']
})
export class RegisterFormComponent implements OnInit {

  regModel: RegistrationFormModel = new RegistrationFormModel();


  constructor(private userService: UserApiService, private router: Router) {
  }

  ngOnInit() {
  }

  register() {

    let registerOp: Observable<UserAuthenticationAttempt> =
      this.userService
        .register(new RegisterRequest(this.regModel));

    registerOp.subscribe(
      attempt => {
        if (attempt.authenticated) {
          //set tokem and stuff
          this.goToDash();
        }
        else {
          //show error
        }
      },
      err => {
        // Log errors if any
        console.log(err);
      });
  }

  goToDash = function () {
    this.router.navigateByUrl('/home');
  };

}
