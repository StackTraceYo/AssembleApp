import {BACK_TO_DASH, CREATE_START, DashActions, MY_GROUPS_RETRIEVED} from '../actions/dashboard-actions';

export interface State {
    currentView: string;
    viewName: string;
    showBack: boolean;
}

export const initialState: State = {
    currentView: 'Dashboard',
    viewName: 'Dashboard',
    showBack: false
};

export function reducer(state = initialState, action: DashActions): State {
    switch (action.type) {
        case MY_GROUPS_RETRIEVED: {
            return {
                ...state,
                currentView: 'Dashboard',
                viewName: 'My Groups',
                showBack: false
            };
        }

        case CREATE_START: {
            return {
                ...state,
                currentView: 'Create A Group',
                viewName: 'Create A Group',
                showBack: true
            };
        }

        case BACK_TO_DASH: {
            return initialState;
        }

        default: {
            return state;
        }
    }
}

export const getCurrentView = (state: State) => state.currentView;
export const getViewName = (state: State) => state.viewName;
export const getShouldShowBack = (state: State) => state.showBack;
