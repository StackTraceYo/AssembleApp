import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LoginRequest} from '../user-api/request/login-request';
import {Store} from '@ngrx/store';
import * as auth from '../reducers/auth-reducer';
import {FormControl, FormGroup} from "@angular/forms";
import {Login} from "../actions/auth-actions";


@Component({
    selector: 'asm-login-form',
    templateUrl: './login-form.component.html',
    styleUrls: ['./login-form.component.scss'],
})
export class LoginFormComponent implements OnInit {


    appname = 'Assemble';
    loggingIn: boolean;

    loginForm: FormGroup = new FormGroup({
        email: new FormControl(''),
        password: new FormControl(''),
    });

    constructor(private router: Router, private store: Store<auth.State>) {
    }

    ngOnInit() {
        this.loggingIn = false;
    }

    login() {
        this.store.dispatch(new Login(new LoginRequest(this.loginForm.value)));


        // const loginOp: Observable<UserAuthenticationAttempt> =
        //     this.userApiService
        //         .login(new LoginRequest(this.user));
        // this.toggleSpinner();
        //
        // loginOp.subscribe(
        //     attempt => {
        //         this.toggleSpinner();
        //         if (attempt.isAuthenticated()) {
        //             // set token/cookie stuff
        //             this.userService.storeUser(attempt.getUser());
        //             this.userService.putToken(attempt.getToken());
        //             this.goToDash();
        //         }
        //         // show error
        //     },
        //     err => {
        //         // Log errors if any
        //         this.toggleSpinner();
        //         console.log(err);
        //     });
    }

    goToRegistration = function () {
        this.router.navigateByUrl('/register');
    };

    goToDash = function () {
        this.router.navigateByUrl('/asm');
    };

    toggleSpinner = function () {
        this.loggingIn = !this.loggingIn;
    };
}
