export class AssembleUser {


    constructor(private _email: string, private _id: string, private _authenticated: boolean) {
    }

    static noUser() {
        return new AssembleUser('', '', false);
    }


    get email(): string {
        return this._email;
    }

    get id(): string {
        return this._id;
    }

    get authenticated(): boolean {
        return this._authenticated;
    }
}
