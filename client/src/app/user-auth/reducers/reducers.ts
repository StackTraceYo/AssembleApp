import { createSelector, createFeatureSelector } from '@ngrx/store';
import * as fromLogin from './login-form-reducer';
import * as core from '../../core/reducers/app-reducers'
import * as fromAuth from './auth-reducer';
import * as fromLoginPage from './login-form-reducer';

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
