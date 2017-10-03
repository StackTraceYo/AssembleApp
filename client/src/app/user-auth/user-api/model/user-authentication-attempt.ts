import {AssembleUser} from '../../assemble.user';

export class UserAuthenticationAttempt {

    constructor(private _authenticated: boolean, private _user: AssembleUser, private _token: string) {
        if (!_authenticated) {
            this._user = AssembleUser.noUser();
        }
    }

    get token(): string {
        return this._token;
    }

    get user(): AssembleUser {
        return this._user;
    }

    get authenticated(): boolean {
        return this._authenticated;
    }
}
