import {createFeatureSelector, createSelector} from '@ngrx/store';
import * as core from '../../core/reducers/app-reducers';
import * as fromMyGroups from './my-group-reducers';

export interface DashboardState {
    myGroups: fromMyGroups.State;
}

export interface State extends core.State {
    dash: DashboardState;
}

export const reducers = {
    myGroups: fromMyGroups.reducer
};

export const selectDashState = createFeatureSelector<DashboardState>('dash');

export const selectMyGroupsState = createSelector(
    selectDashState,
    (state: DashboardState) => state.myGroups
);
export const getLoggedIn = createSelector(
    selectDashState,
    (state: DashboardState) => state.myGroups.myGroups
);
