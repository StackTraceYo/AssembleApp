import {AssembleUser} from '../assemble.user';
import {AuthActions, LOGIN_SUCCESS, LOGOUT} from '../actions/auth-actions';

export interface State {
    authenticated: boolean;
    user: AssembleUser | null;
}

export const initialState: State = {
    authenticated: false,
    user: null,
};

export function reducer(state = initialState, action: AuthActions): State {
    switch (action.type) {
        case LOGIN_SUCCESS: {
            return {
                ...state,
                authenticated: true,
                user: action.payload.user,
            };
        }

        case LOGOUT: {
            return initialState;
        }

        default: {
            return state;
        }
    }
}


export const isAuthenticated = (state: State) => state.authenticated;
export const getUser = (state: State) => state.user;
