import {Injectable} from '@angular/core';
import {HttpClient} from '../../http/http-client/http-client';
import {ContentRequest} from './request/content-request';

@Injectable()
export class ContentService {

    constructor(private httpClient: HttpClient) {

    }


    getContent(req: ContentRequest): any {
        return this.httpClient.post('/api/content/retrieve', JSON.stringify(req));
    }


}
