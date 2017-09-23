import {AssembleUser} from "../../assemble.user";

export class UserAuthenticationAttempt {

  constructor(private _authenticated: boolean, private _user: AssembleUser) {
    if (!_authenticated) {
      this._user = AssembleUser.noUser();
    }
  }

  get user(): AssembleUser {
    return this._user;
  }

  get authenticated(): boolean {
    return this._authenticated;
  }
}
