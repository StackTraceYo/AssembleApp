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

  removeUser() {
    this._user.next(AssembleUser.noUser());
  }

  getUser() {
    return this._user.asObservable();
  }

}
