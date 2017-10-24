import {CATEGORIES_RETRIEVED, DashActions} from '../actions/dashboard-actions';
import {Category} from '../../content/model/category';

export interface State {
    categories: Category;
}

export const initialState: State = {
    categories: Category.emptyCategory()
};

export function reducer(state = initialState, action: DashActions): State {
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
