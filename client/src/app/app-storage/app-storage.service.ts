import {Injectable} from '@angular/core';

@Injectable()
export class AppStorageService {

    constructor() {
    }

    store(key: string, content: string) {
        localStorage.setItem(key, content);
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
