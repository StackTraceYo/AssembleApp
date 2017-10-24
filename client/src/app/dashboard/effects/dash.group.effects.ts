import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/exhaustMap';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/take';
import {Injectable} from '@angular/core';
import {Actions, Effect} from '@ngrx/effects';
import {of} from 'rxjs/observable/of';
import * as Dash from '../actions/dashboard-actions';
import {CategoriesRetrieved, RetrieveCategories} from '../actions/dashboard-actions';
import {GroupApiService} from '../../group/group-api/group-api.service';
// import * as lo from 'lodash';
import {GroupListResponse} from '../../group/group-api/response/group-list-response';
import {ContentService} from '../../content/content-api/content.service';
import {ContentRequest} from '../../content/content-api/request/content-request';
import {Category} from "../../content/model/category";

@Injectable()
export class DashGroupEffects {

    @Effect()
    myGroups$ = this.actions$
        .ofType(Dash.RETRIEVE_MY_GROUPS)
        .map((action: Dash.RetrieveMyGroups) => action.payload)
        .exhaustMap(req =>

            this.groupService.list(req.request, req.token)
                .map(resp => {
                    const response: GroupListResponse = resp.json();
                    return response.list;
                })
                .map(groups => {
                    return new Dash.MyGroupsRetrieved({
                        groups: groups
                    });
                })
                .catch(error => of(new Dash.RetrievalFailure(error)))
        );

    @Effect()
    createGroupStart$ = this.actions$
        .ofType(Dash.CREATE_START)
        .map(x => {
            return new RetrieveCategories({request: new ContentRequest('CATEGORY')});
        });

    @Effect()
    myCategories$ = this.actions$
        .ofType(Dash.RETRIEVE_CATEGORIES)
        .map((action: Dash.RetrieveCategories) => action.payload)
        .exhaustMap(req =>
            this.contentService.getContent(req.request)
                .map(resp => {
                    const cat: Category = resp.json();
                    return new CategoriesRetrieved({categories: cat});
                })
                .catch(error => of(new Dash.RetrievalFailure(error)))
        );


    constructor(private actions$: Actions,
                private groupService: GroupApiService,
                private contentService: ContentService) {
    }
}
