export class CreateGroupModel {

    constructor(private _groupName: string) {
    }

    get groupName(): string {
        return this._groupName;
    }

    set groupName(value: string) {
        this._groupName = value;
    }
}
