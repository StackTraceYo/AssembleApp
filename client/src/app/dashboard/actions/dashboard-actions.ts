import {Action} from '@ngrx/store';
import {GroupListRequest} from '../../group/group-api/request/group-list-request';
import {AssembleApiGroup} from '../../group/group-api/assemble-api-group';
import {ContentRequest} from '../../content/content-api/request/content-request';
import {Category} from '../../content/model/category';
import {CreateRequest} from "../../group/group-api/request/create-request";
import {CreateResponse} from "../../group/group-api/response/create-response";


export const RETRIEVE_CATEGORIES = '[Dash] Get Categories';
export const CATEGORIES_RETRIEVED = '[Dash] Retrieved Categories';
export const RETRIEVE_MY_GROUPS = '[Dash] My Groups';
export const MY_GROUPS_RETRIEVED = '[Dash] Retrieved My Groups';
export const FAILED_TO_RETRIEVE = '[Dash] Failed To Retrieve';
export const BACK_TO_DASH = '[Dash] Back To Dash';

export const CREATE_START = '[Dash] Starting Create Group';
export const CREATE_END = '[Dash] Create Group Ended Successfully';
export const CREATE_GROUP = '[Create] Creating Group';
export const FAILED_TO_CREATE = '[Create] Failed To Create';
export const GROUP_CREATED = '[Create] Created Group';

export const UPDATE_CATEGORY = '[Create Dash] Update Category';


export class RetrieveMyGroups implements Action {
    readonly type = RETRIEVE_MY_GROUPS;

    constructor(public payload: {
        request: GroupListRequest,
        token: string
    }) {
    }
}

export class MyGroupsRetrieved implements Action {
    readonly type = MY_GROUPS_RETRIEVED;

    constructor(public payload: { groups: AssembleApiGroup[] }) {
    }
}

export class RetrieveCategories implements Action {
    readonly type = RETRIEVE_CATEGORIES;

    constructor(public payload: {
        request: ContentRequest
    }) {
    }
}

export class CategoriesRetrieved implements Action {
    readonly type = CATEGORIES_RETRIEVED;

    constructor(public payload: { categories: Category }) {
    }
}

export class RetrievalFailure implements Action {
    readonly type = FAILED_TO_RETRIEVE;

    constructor(public payload: { msg: string }) {
    }
}

export class CreateGroupStart implements Action {
    readonly type = CREATE_START;

    constructor() {
    }
}

export class BackToDash implements Action {
    readonly type = BACK_TO_DASH;

    constructor() {
    }
}

export class CreateGroup implements Action {
    readonly type = CREATE_GROUP;

    constructor(public payload: {
        request: CreateRequest,
        token: string
    }) {
    }
}

export class GroupCreated implements Action {
    readonly type = GROUP_CREATED;

    constructor(public payload: { group: CreateResponse }) {
    }
}

export class CreationFailure implements Action {
    readonly type = FAILED_TO_CREATE;

    constructor(public payload: { msg: string }) {
    }
}

export class CreationCompleted implements Action {
    readonly type = CREATE_END;

    constructor(public payload: { msg: string }) {
    }
}

export class UpdateCategory implements Action {
    readonly type = UPDATE_CATEGORY;

    constructor(public payload: {
        category: Category,
        categoryStatus: any
    }) {
    }
}


export type DashActions =
    | RetrieveMyGroups
    | MyGroupsRetrieved
    | RetrieveCategories
    | CategoriesRetrieved
    | RetrievalFailure
    | CreateGroupStart
    | BackToDash
    | GroupCreated
    | CreationFailure
    | CreationCompleted
    | UpdateCategory;



