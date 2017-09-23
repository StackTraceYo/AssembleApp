import {Component, OnInit} from '@angular/core';
import {UserApiService} from "../user-api/user-api.service";
import {RegisterRequest} from "../user-api/request/register-request";
import {Observable} from "rxjs";
import {RegistrationFormModel} from "./model/registeration-form-model";
import {UserAuthenticationAttempt} from "../user-api/model/user-authentication-attempt";
import {Router} from "@angular/router";
import {UserService} from "../user-service";

@Component({
  selector: 'asm-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent implements OnInit {

  regModel: RegistrationFormModel = new RegistrationFormModel();


  constructor(private userApiService: UserApiService, private userService: UserService, private router: Router) {
  }

  ngOnInit() {
  }

  register() {

    let registerOp: Observable<UserAuthenticationAttempt> =
      this.userApiService
        .register(new RegisterRequest(this.regModel));

    registerOp.subscribe(
      attempt => {
        if (attempt.authenticated) {
          //set tokem and stuff
          this.userService.storeUser(attempt.user);
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
    this.router.navigateByUrl('/asm');
  };

}
