import {Injectable} from '@angular/core';
import {CanActivate} from '@angular/router';

import {Store} from '@ngrx/store';
import * as Auth from '../actions/auth-actions';
import * as fromAuth from '../reducers/reducers';

@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private store: Store<fromAuth.State>) {
    }

    canActivate(): any {
        // return true;
        return this.store
            .select(fromAuth.getLoggedIn)
            .map(authed => {
                if (!authed) {
                    this.store.dispatch(new Auth.LoginRedirect());
                    return false;
                }

                return true;
            })
            .take(1);
    }
}

