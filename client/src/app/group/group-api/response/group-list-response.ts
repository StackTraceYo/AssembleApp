import {AssembleApiGroup} from '../assemble-api-group';

export class GroupListResponse {

    host: AssembleApiGroup[];
    guest: AssembleApiGroup[];


    constructor(host: AssembleApiGroup[], guest: AssembleApiGroup[]) {
        this.host = host;
        this.guest = guest;
    }


}
