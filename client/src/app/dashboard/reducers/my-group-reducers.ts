import {DashActions, MY_GROUPS_RETRIEVED} from '../actions/dashboard-actions';
import {AssembleApiGroup} from '../../group/group-api/assemble-api-group';

export interface State {
    myGroups: AssembleApiGroup[];
}

export const initialState: State = {
    myGroups: []
};

export function reducer(state = initialState, action: DashActions): State {
    switch (action.type) {
        case MY_GROUPS_RETRIEVED: {
            return {
                ...state,
                myGroups: action.payload.groups,
            };
        }

        default: {
            return state;
        }
    }
}

export const getMyGroups = (state: State) => state.myGroups;
