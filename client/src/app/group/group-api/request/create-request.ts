import {CreateGroupForm} from '../../../dashboard/reducers/create-group-reducers';

export class CreateRequest {

    groupName: string;
    categoryName: string;
    details: {
        max: number;
    };

    constructor(private form: CreateGroupForm) {
        this.categoryName = form.categoryName;
        this.groupName = form.groupName;
        this.details = {max: 2};
    }

}
