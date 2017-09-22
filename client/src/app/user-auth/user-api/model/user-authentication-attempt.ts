import {AssembleUser} from "../../assemble.user.";

export class UserAuthenticationAttempt {

  private _user: AssembleUser;
  private _authenticated: boolean;


  constructor(success: boolean, user: AssembleUser) {
    if (success) {
      this._user = user;
      this._authenticated = true;

    } else {
      this._authenticated = false;
    }
  }


  get user(): AssembleUser {
    return this._user;
  }

  get authenticated(): boolean {
    return this._authenticated;
  }
}
