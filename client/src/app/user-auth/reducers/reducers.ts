import {createFeatureSelector, createSelector} from '@ngrx/store';
import * as fromLogin from './login-form-reducer';
import * as fromLoginPage from './login-form-reducer';
import * as core from '../../core/reducers/app-reducers'
import * as fromAuth from './auth-reducer';
import * as fromRegisterPage from './register-form-reducer';

export interface UserAuthState {
    status: fromAuth.State;
    loginPage: fromLogin.State;
    registerPage: fromRegisterPage.State;
}

export interface State extends core.State {
    auth: UserAuthState;
}

export const reducers = {
    loginPage: fromLogin.reducer,
    registerPage: fromRegisterPage.reducer,
    auth: fromAuth.reducer
};

export const selectAuthState = createFeatureSelector<UserAuthState>('auth');

export const selectAuthStatusState = createSelector(
    selectAuthState,
    (state: UserAuthState) => state.status
);
export const getLoggedIn = createSelector(
    selectAuthStatusState,
    fromAuth.isAuthenticated
);
export const getUser = createSelector(selectAuthStatusState, fromAuth.getUser);

export const selectLoginPageState = createSelector(
    selectAuthState,
    (state: UserAuthState) => state.loginPage
);
export const getLoginPageError = createSelector(
    selectLoginPageState,
    fromLoginPage.getError
);
export const getLoginPagePending = createSelector(
    selectLoginPageState,
    fromLoginPage.getPending
);

export const selectRegisterPageState = createSelector(
    selectAuthState,
    (state: UserAuthState) => state.loginPage
);
export const getRegisterPageError = createSelector(
    selectRegisterPageState,
    fromRegisterPage.getError
);
export const getRegisterPagePending = createSelector(
    selectRegisterPageState,
    fromRegisterPage.getPending
);
