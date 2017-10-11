import {Injectable} from '@angular/core';
import {HttpClient} from '../../http/http-client/http-client';
import {ContentRequest} from './request/content-request';
import {Observable} from 'rxjs/Observable';
import {Response} from '@angular/http';
import {Category} from '../model/category';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

@Injectable()
export class ContentService {

    $categories = new BehaviorSubject<Category>(Category.emptyCategory());
    categories: Category;

    constructor(private httpClient: HttpClient) {

    }

    getCategories(): Observable<Category> {
        this.getContent('CATEGORY')
            .subscribe(res => {
                this.categories = res;
                this.$categories.next(this.categories);
            });
        return this.$categories.asObservable();
    }

    getCategoriesInstant(): Category {
        return this.categories;
    }

    private getContent(contentName: string): Observable<any> {
        const req = new ContentRequest(contentName);
        return this.httpClient.post('/api/content/retrieve', JSON.stringify(req))
            .map((res: Response) => {
                return res.json();
            })
            .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    }


}
