import {Component, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import {Router} from '@angular/router';
import {FormControl, FormGroup} from '@angular/forms';
import * as fromAuth from '../reducers/reducers';
import * as registerForm from '../reducers/register-form-reducer';
import {RegisterRequest} from '../user-api/request/register-request';
import {Register} from '../actions/auth-actions';


@Component({
    selector: 'asm-register-form',
    templateUrl: './register-form.component.html',
    styleUrls: ['./register-form.component.scss']
})
export class RegisterFormComponent implements OnInit {

    regForm = new FormGroup({
        email: new FormControl(''),
        password: new FormControl(''),
    });
    pending$ = this.store.select(fromAuth.getRegisterPagePending);
    error$ = this.store.select(fromAuth.getRegisterPageError);

    constructor(private router: Router, private store: Store<registerForm.State>) {
    }

    ngOnInit() {
    }

    register() {
        this.store.dispatch(new Register(new RegisterRequest(this.regForm.value)));
    }

}
