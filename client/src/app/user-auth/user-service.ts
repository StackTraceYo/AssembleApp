import {Injectable, OnInit} from '@angular/core';
import {AssembleUser} from './assemble.user';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {AppStorageService} from '../app-storage/app-storage.service';

@Injectable()
export class UserService implements OnInit {


    private _$user = new BehaviorSubject<AssembleUser>(AssembleUser.noUser());
    private _$token = new BehaviorSubject<String>('');

    private _user = AssembleUser.noUser();
    private _token = '';

    constructor(private storageService: AppStorageService) {
    }

    ngOnInit(): void {
        // const storedUser = AppStorageService.retrieve('asm-user');
        // if (storedUser) {
        //   this._user = JSON.parse(storedUser);
        // }
        // this._token = AppStorageService.retrieve('asm-token');
    }

    storeUser(user: AssembleUser) {
        this._user = user;
        this._$user.next(user);
        // AppStorageService.store('asm-user', JSON.stringify(user));
    }


    removeUser() {
        this._$user.next(AssembleUser.noUser());
    }

    getUser() {
        return this._$user.asObservable();
    }

    putToken(token: string) {
        const newToken = token;
        this._$token.next(token);
        // AppStorageService.store('asm-token', JSON.stringify(token));
    }

    getToken() {
        return this._$token.asObservable();
    }

    clearToken() {
        this._$token.next('');
    }
}
