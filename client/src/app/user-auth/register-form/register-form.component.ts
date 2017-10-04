import {Component, OnInit} from '@angular/core';
import {UserApiService} from '../user-api/user-api.service';
import {RegisterRequest} from '../user-api/request/register-request';
import {Observable} from 'rxjs/Observable';
import {RegistrationFormModel} from './model/registeration-form-model';
import {UserAuthenticationAttempt} from '../user-api/model/user-authentication-attempt';
import {Router} from '@angular/router';
import {UserService} from '../user-service';

@Component({
    selector: 'asm-register-form',
    templateUrl: './register-form.component.html',
    styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent implements OnInit {

    regModel: RegistrationFormModel = new RegistrationFormModel();
    registering = false;
    toggleSpinner = function () {
        this.registering = !this.registering;
    };
    goToDash = function () {
        this.router.navigateByUrl('/asm');
    };

    constructor(private userApiService: UserApiService, private userService: UserService, private router: Router) {
    }

    ngOnInit() {
    }

    register() {

        const registerOp: Observable<UserAuthenticationAttempt> =
            this.userApiService
                .register(new RegisterRequest(this.regModel));

        this.toggleSpinner();
        registerOp.subscribe(
            attempt => {
                this.toggleSpinner();
                if (attempt.authenticated) {
                    // set tokem and stuff
                    this.userService.storeUser(attempt.user);
                    this.userService.putToken(attempt.token);
                    this.goToDash();
                }
                // else {
                //   // show error
                // }
            },
            err => {
                // Log errors if any
                this.toggleSpinner();
                console.log(err);
            });
    }

}
