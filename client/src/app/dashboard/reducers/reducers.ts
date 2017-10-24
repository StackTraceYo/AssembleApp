import {createFeatureSelector, createSelector} from '@ngrx/store';
import * as core from '../../core/reducers/app-reducers';
import * as fromMyGroups from './my-group-reducers';
import * as fromCreate from './create-group-reducers';
import * as fromMainDash from './dash-reducers';

export interface DashboardState {
    myGroups: fromMyGroups.State;
    dashboard: fromMainDash.State;
    createGroup: fromCreate.State;
}

export interface State extends core.State {
    dash: DashboardState;
}

export const reducers = {
    myGroups: fromMyGroups.reducer,
    dashboard: fromMainDash.reducer,
    createGroup: fromCreate.reducer
};

export const selectDashState = createFeatureSelector<DashboardState>('dash');


export const selectDashboardState = createSelector(
    selectDashState,
    (state: DashboardState) => state.dashboard
);

export const selectCreateState = createSelector(
    selectDashState,
    (state: DashboardState) => state.createGroup
);

export const selectViewName = createSelector(
    selectDashboardState,
    fromMainDash.getViewName
);


export const selectCurrentView = createSelector(
    selectDashboardState,
    fromMainDash.getCurrentView
);

export const selectShowBack = createSelector(
    selectDashboardState,
    fromMainDash.getShouldShowBack
);

export const selectCategories = createSelector(
    selectCreateState,
    fromCreate.getCategories
);
