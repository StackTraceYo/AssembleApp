import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Store} from '@ngrx/store';
import * as auth from '../user-auth/reducers/reducers';
import {Login, Logout} from '../user-auth/actions/auth-actions';

@Component({
    selector: 'asm-toolbar',
    templateUrl: './toolbar.component.html',
    styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

    LOGGED_IN = 'Logout';
    LOGGED_OUT = 'Login';
    loggedIn$ = this.store.select(auth.getLoggedIn);
    assembleLink = '/login';

    constructor(private router: Router, private store: Store<auth.State>) {
    }

    ngOnInit() {
    }

    loggedInOrOut() {
        const text =  this.loggedIn$ ? this.LOGGED_IN : this.LOGGED_OUT;
        return text;
    }

    toggleUserSignedIn() {
        this.loggedIn$ ? this.logout() : this.login();
    }

    logout() {
        this.store.dispatch(new Logout());
    }

    login() {
        this.gotoLogin();
    }

    gotoLogin() {
        this.router.navigate([this.assembleLink]);
    }

}
