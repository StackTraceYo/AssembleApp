import * as fromAuth from './auth-reducer';
import * as fromLogin from './login-form-reducer';
import * as core from '../../core/reducers/app-reducers'

export interface UserAuthState {
    status: fromAuth.State;
    loginPage: fromLogin.State;
}

export interface State extends core.State {
    auth: UserAuthState;
}


export const reducers = {
    loginPage: fromLogin.reducer,
    auth: fromAuth.reducer
};
