import {Injectable} from '@angular/core';

@Injectable()
export class AppStorageService {

    constructor() {
    }

    static store(key: string, content: Object) {
        localStorage.setItem(key, JSON.stringify(content));
    }

    static retrieve(key: string) {
        return localStorage.getItem(key);
    }

    static exists(key: string) {
        return Boolean(localStorage.getItem(key));
    }

}
