import {Action} from '@ngrx/store';
import {LoginRequest} from '../user-api/request/login-request';
import {AssembleUser} from '../assemble.user';
import {RegisterRequest} from '../user-api/request/register-request';

export const LOGIN = '[Auth] Login';
export const LOGOUT = '[Auth] Logout';
export const LOGIN_SUCCESS = '[Auth] Login Success';
export const LOGIN_FAILURE = '[Auth] Login Failure';
export const LOGIN_REDIRECT = '[Auth] Login Redirect';
export const PAGE_REDIRECT = '[Auth] Page Redirect';
export const REGISTER = '[Auth] Register';
export const REGISTER_SUCCESS = '[Auth] Register Success';
export const REGISTER_FAILURE = '[Auth] Register Failure';
export const REGISTER_REDIRECT = '[Auth] Register Redirect';


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

export class PageRedirect implements Action {
    readonly type = PAGE_REDIRECT;

    constructor(public payload: { to: string, data: any }) {
    }
}

export class Logout implements Action {
    readonly type = LOGOUT;
}

export class Register implements Action {
    readonly type = REGISTER;

    constructor(public payload: RegisterRequest) {
    }
}

export class RegisterSuccess implements Action {
    readonly type = REGISTER_SUCCESS;

    constructor(public payload: { user: AssembleUser, token: string }) {
    }
}

export class RegisterFailure implements Action {
    readonly type = REGISTER_FAILURE;

    constructor(public payload: any) {
    }
}

export class RegisterRedirect implements Action {
    readonly type = REGISTER_REDIRECT;
}


export type AuthActions =
    | Login
    | LoginSuccess
    | LoginFailure
    | LoginRedirect
    | Logout
    | Register
    | RegisterSuccess
    | RegisterFailure
    | RegisterRedirect
    | PageRedirect;

