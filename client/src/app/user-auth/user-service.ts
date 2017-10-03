import {Injectable} from '@angular/core';
import {AssembleUser} from './assemble.user';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

@Injectable()
export class UserService {

    private _user = new BehaviorSubject<AssembleUser>(AssembleUser.noUser());
    private _token = new BehaviorSubject<String>('');

    constructor() {
    }

    storeUser(user: AssembleUser) {
        const users = user;
        this._user.next(user);
    }


    removeUser() {
        this._user.next(AssembleUser.noUser());
    }

    getUser() {
        return this._user.asObservable();
    }

    putToken(token: string) {
        const newToken = token;
        this._token.next(token);
    }

    getToken() {
        return this._token.asObservable();
    }

    clearToken() {
        this._token.next('');
    }
}
