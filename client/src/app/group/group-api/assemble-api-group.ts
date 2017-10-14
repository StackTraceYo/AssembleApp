export class AssembleApiGroup {

    groupId: string;
    name: string;
    category: string;

    constructor(groupId: string, name: string, category: string) {
        this.groupId = groupId;
        this.category = category;
        this.name = name;
    }
}
