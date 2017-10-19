import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/exhaustMap';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/take';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Actions, Effect} from '@ngrx/effects';
import {of} from 'rxjs/observable/of';

import * as Auth from '../actions/auth-actions';
import {UserApiService} from '../user-api/user-api.service';
import {AssembleUser} from '../assemble.user';
import {AppStorageService} from '../../app-storage/app-storage.service';

@Injectable()
export class AuthEffects {
    @Effect()
    login$ = this.actions$
        .ofType(Auth.LOGIN)
        .map((action: Auth.Login) => action.payload)
        .exhaustMap(auth =>
            this.authService.login(auth)
                .map(resp => {
                    const response = resp.json();
                    if (response.success) {
                        const token = UserApiService.extractHeader(resp);
                        return new Auth.LoginSuccess({
                            user: new AssembleUser(response.user.email, response.user.id, true),
                            token
                        });
                    } else {
                        return new Auth.LoginFailure(response.msg);
                    }
                })
                .catch(error => of(new Auth.LoginFailure(error)))
        );

    @Effect({dispatch: false})
    loginSuccess$ = this.actions$
        .ofType(Auth.LOGIN_SUCCESS)
        .map((action: Auth.LoginSuccess) => action.payload)
        .do(suc => {
            this.appStorage.store('asm-token', suc.token);
            this.appStorage.store('asm-user', suc.user.id);
        })
        .do(() => this.router.navigate(['/asm']));

    @Effect({dispatch: false})
    loginRedirect$ = this.actions$
        .ofType(Auth.LOGIN_REDIRECT, Auth.LOGOUT)
        .do(authed => {
            this.router.navigate(['/login']);
        });

    constructor(private actions$: Actions,
                private authService: UserApiService,
                private appStorage: AppStorageService,
                private router: Router) {
    }
}
