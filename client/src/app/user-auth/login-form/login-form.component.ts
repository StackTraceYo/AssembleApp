import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LoginRequest} from '../user-api/request/login-request';
import {Store} from '@ngrx/store';
import * as fromAuth from '../reducers/reducers';
import {selectAuthStatusState} from '../reducers/reducers';
import * as auth from '../reducers/auth-reducer';
import {FormControl, FormGroup} from '@angular/forms';
import {Login} from '../actions/auth-actions';


@Component({
    selector: 'asm-login-form',
    templateUrl: './login-form.component.html',
    styleUrls: ['./login-form.component.scss'],
})
export class LoginFormComponent implements OnInit {


    appname = 'Assemble';
    pending$ = this.store.select(fromAuth.getLoginPagePending);
    error$ = this.store.select(fromAuth.getLoginPageError);

    loginForm: FormGroup = new FormGroup({
        email: new FormControl(''),
        password: new FormControl(''),
    });

    constructor(private router: Router, private store: Store<auth.State>) {
    }

    ngOnInit() {
        this.store.select(selectAuthStatusState).subscribe(state => {
                if (state.authenticated) {
                    this.router.navigateByUrl('/asm');
                }
            }
        );
    }

    login() {
        this.store.dispatch(new Login(new LoginRequest(this.loginForm.value)));
    }

    goToRegistration = function () {
        this.router.navigateByUrl('/register');
    };
}
