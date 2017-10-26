import {CATEGORIES_RETRIEVED, DashActions, UPDATE_CATEGORY} from '../actions/dashboard-actions';
import {Category} from '../../content/model/category';
import {createFormGroupState, formGroupReducer, FormGroupState} from 'ngrx-forms';

export const FORM_ID = 'create-group-form-id';

export interface CreateGroupForm {
    groupName: string;
    categoryName: string;
}

export interface State {
    categories: Category;
    createGroup: FormGroupState<CreateGroupForm>;
    categoryStatus: {
        isFinal: boolean;
        categoryName: string;
    };
}

const initialFormState = createFormGroupState<CreateGroupForm>(FORM_ID, {
    groupName: '',
    categoryName: ''
});

export const initialState: State = {
    categories: Category.emptyCategory(),
    createGroup: initialFormState,
    categoryStatus: {
        isFinal: false,
        categoryName: ''
    }
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
        case UPDATE_CATEGORY: {
            return {
                ...state,
                categories: action.payload.category,
                categoryStatus: action.payload.categoryStatus
            };
        }

        default: {
            return state;
        }
    }
}

export const getCategories = (state: State) => state.categories;
export const getCreateForm = (state: State) => state.createGroup;
export const getSelectedCategoryStatus = (state: State) => state.categoryStatus;
