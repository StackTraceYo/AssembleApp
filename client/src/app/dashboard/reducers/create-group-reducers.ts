import {CATEGORIES_RETRIEVED, DashActions} from '../actions/dashboard-actions';
import {Category} from '../../content/model/category';
import {createFormGroupState, formGroupReducer, FormGroupState} from 'ngrx-forms';

const FORM_ID = 'create-group-form-id';

export interface CreateGroupForm {
    categoryName: string;
    groupName: string;
}

export interface State {
    categories: Category;
    createGroup: FormGroupState<CreateGroupForm>;
}

const initialFormState = createFormGroupState<CreateGroupForm>(FORM_ID, {
    categoryName: '',
    groupName: ''
});

export const initialState: State = {
    categories: Category.emptyCategory(),
    createGroup: initialFormState
};

export function reducer(state = initialState, action: DashActions): State {
    const createGroup = formGroupReducer(state.createGroup, action);
    if (createGroup !== state.createGroup) {
        state = {...state, createGroup};
    }
    switch (action.type) {
        case CATEGORIES_RETRIEVED: {
            return {
                ...state,
                categories: action.payload.categories,
            };
        }

        default: {
            return state;
        }
    }
}

export const getCategories = (state: State) => state.categories;
export const getCreateForm = (state: State) => state.createGroup;
