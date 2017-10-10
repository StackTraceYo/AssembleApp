import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptions} from '@angular/http';

@Injectable()
export class HttpClient {


    private headers: Headers = new Headers({'Content-Type': 'application/json'});
    private options: RequestOptions = new RequestOptions({headers: this.headers});
    private BASE_URL: String = 'http://localhost:9000';

    constructor(private http: Http) {
    }


    get(url, token = '') {
        this.options.headers.set('X-Asm-Auth', token);
        return this.http.get(this.createFullUrl(url), this.options);
    }

    post(url, data, token = '') {
        this.options.headers.set('X-Asm-Auth', token);
        return this.http.post(this.createFullUrl(url), data, this.options);
    }

    private getRequestOptions() {
        return this.options;
    }

    private createFullUrl(url) {
        return this.BASE_URL + url;
    }
}
