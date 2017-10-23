import {Action} from '@ngrx/store';
import {GroupListRequest} from '../../group/group-api/request/group-list-request';
import {AssembleApiGroup} from '../../group/group-api/assemble-api-group';


export const RETRIEVE_MY_GROUPS = '[Dash] My Groups';
export const MY_GROUPS_RETRIEVED = '[Dash] Retrieved My Groups';
export const FAILED_TO_RETRIEVE = '[Dash] Failed To Retrieve';


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

export class RetrievalFailure implements Action {
    readonly type = FAILED_TO_RETRIEVE;

    constructor(public payload: { msg: string }) {
    }
}


export type DashActions =
    | RetrieveMyGroups
    | MyGroupsRetrieved
    | RetrievalFailure;

