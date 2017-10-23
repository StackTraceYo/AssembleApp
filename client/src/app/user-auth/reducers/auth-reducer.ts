import {AssembleUser} from '../assemble.user';
import {AuthActions, LOGIN_SUCCESS, LOGOUT} from '../actions/auth-actions';

export interface State {
    authenticated: boolean;
    user: AssembleUser | null;
    token: string | null;
}

export const initialState: State = {
    authenticated: !!localStorage.getItem('asm-token'),
    user: localStorage.getItem('asm-user') ? JSON.parse(localStorage.getItem('asm-user')) : null,
    token: localStorage.getItem('asm-token')
};

export function reducer(state = initialState, action: AuthActions): State {
    switch (action.type) {
        case LOGIN_SUCCESS: {
            return {
                ...state,
                authenticated: true,
                user: action.payload.user,
                token: action.payload.token
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
