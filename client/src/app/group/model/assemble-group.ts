import {AssembleApiGroup} from "../group-api/assemble-api-group";

export class AssembleGroup {

    groupId: string;
    name: string;
    category: string;


    constructor(group: AssembleApiGroup) {
        this.groupId = group.groupId;
        this.category = group.category;
        this.name = group.name;
    }
}
