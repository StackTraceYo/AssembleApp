import {Injectable} from '@angular/core';
import {AssembleUser} from "./assemble.user";
import {Subject} from "rxjs/Subject";
import {BehaviorSubject} from "rxjs/BehaviorSubject";

@Injectable()
export class UserService {

  private _user = new BehaviorSubject<AssembleUser>(AssembleUser.noUser());

  constructor() {
  }

  storeUser(user: AssembleUser) {
    let users = user;
    this._user.next(user);
  }

  removeUser(user: AssembleUser) {
    this._user = null;
    this._user.next(user);
  }

  getUser() {
    return this._user.asObservable();
  }

}
