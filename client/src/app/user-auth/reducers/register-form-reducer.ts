import {AuthActions, REGISTER, REGISTER_FAILURE, REGISTER_SUCCESS} from '../actions/auth-actions';


export interface State {
    error: string | null;
    pending: boolean;
}

export const initialState: State = {
    error: null,
    pending: false,
};

export function reducer(state = initialState, action: AuthActions): State {
    switch (action.type) {
        case REGISTER: {
            return {
                ...state,
                error: null,
                pending: true,
            };
        }

        case REGISTER_SUCCESS: {
            return {
                ...state,
                error: null,
                pending: false,
            };
        }

        case REGISTER_FAILURE: {
            return {
                ...state,
                error: action.payload,
                pending: false,
            };
        }

        default: {
            return state;
        }
    }
}

export const getError = (state: State) => state.error;
export const getPending = (state: State) => state.pending;
