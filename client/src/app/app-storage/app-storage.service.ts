import {Injectable} from '@angular/core';

@Injectable()
export class AppStorageService {

    constructor() {
    }

    store(key: string, content: Object) {
        localStorage.setItem(key, JSON.stringify(content));
    }

    retrieve(key: string) {
        return localStorage.getItem(key);
    }

    exists(key: string) {
        return Boolean(localStorage.getItem(key));
    }

    clear() {
        localStorage.clear();
    }

}
