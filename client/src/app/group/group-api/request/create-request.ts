import {CreateGroupModel} from '../model/create-group-model';

export class CreateRequest {

    groupName: string;

    constructor(formModel: CreateGroupModel) {
        this.groupName = formModel.groupName;
    }


}
