import {CreateGroupModel} from '../../../dashboard/create-group/model/create-group-model';

export class CreateRequest {

    groupName: string;

    constructor(formModel: CreateGroupModel) {
        this.groupName = formModel.groupName;
    }


}
