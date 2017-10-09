export class CreateGroupModel {

    private _groupName: string;

    constructor() {
    }

    get groupName(): string {
        return this._groupName;
    }

    set groupName(value: string) {
        this._groupName = value;
    }
}
