import {Action} from '@ngrx/store';
import {LoginRequest} from '../user-api/request/login-request';
import {AssembleUser} from '../assemble.user';

export const LOGIN = '[Auth] Login';
export const LOGOUT = '[Auth] Logout';
export const LOGIN_SUCCESS = '[Auth] Login Success';
export const LOGIN_FAILURE = '[Auth] Login Failure';
export const LOGIN_REDIRECT = '[Auth] Login Redirect';

export class Login implements Action {
    readonly type = LOGIN;

    constructor(public payload: LoginRequest) {
    }
}

export class LoginSuccess implements Action {
    readonly type = LOGIN_SUCCESS;

    constructor(public payload: { user: AssembleUser, token: string }) {
    }
}

export class LoginFailure implements Action {
    readonly type = LOGIN_FAILURE;

    constructor(public payload: any) {
    }
}

export class LoginRedirect implements Action {
    readonly type = LOGIN_REDIRECT;
}

export class Logout implements Action {
    readonly type = LOGOUT;
}

export type AuthActions =
    | Login
    | LoginSuccess
    | LoginFailure
    | LoginRedirect
    | Logout;
