import {AssembleApiGroup} from '../assemble-api-group';

export class GroupListResponse {

    list: AssembleApiGroup[];


    constructor(list: AssembleApiGroup[]) {
        this.list = list;
    }


}
