import {Injectable, OnInit} from '@angular/core';
import {AssembleUser} from './assemble.user';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {AppStorageService} from '../app-storage/app-storage.service';

@Injectable()
export class UserService implements OnInit {


    private _user = AssembleUser.noUser();
    private _token = '';

    private _$user: BehaviorSubject<AssembleUser>;
    private _$token: BehaviorSubject<String>;

    constructor(private storageService: AppStorageService) {
        const storedUser = this.storageService.retrieve('asm-user');
        if (storedUser) {
            const parsedStoredUser = JSON.parse(storedUser);
            this._user = new AssembleUser(parsedStoredUser.email, parsedStoredUser.id, true);
        }
        this._token = this.storageService.retrieve('asm-token');
        this._$token = new BehaviorSubject<String>(this._token);
        this._$user = new BehaviorSubject<AssembleUser>(this._user);
    }

    ngOnInit(): void {
    }

    storeUser(user: AssembleUser) {
        this._user = user;
        this._$user.next(this._user);
        this.storageService.store('asm-user', user);
    }


    removeUser() {
        this._$user.next(AssembleUser.noUser());
    }

    getUser() {
        return this._$user.asObservable();
    }

    putToken(token: string) {
        this._token = token;
        this._$token.next(this._token);
        this.storageService.store('asm-token', token);
    }

    getToken() {
        return this._$token.asObservable();
    }

    getInstantToken() {
        return this._token
    }

    clearToken() {
        this._$token.next('');
    }
}
