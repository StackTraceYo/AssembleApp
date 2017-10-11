export class CreateResponse {

    groupId: string;
    success: boolean;


    constructor(groupId: string, success: boolean) {
        this.groupId = groupId;
        this.success = success;
    }
}
