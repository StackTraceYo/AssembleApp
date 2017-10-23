import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/exhaustMap';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/take';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Actions, Effect} from '@ngrx/effects';
import {of} from 'rxjs/observable/of';
import * as Dash from '../actions/dashboard-actions';
import {GroupApiService} from '../../group/group-api/group-api.service';

@Injectable()
export class DashGroupEffects {

    @Effect()
    myGroups$ = this.actions$
        .ofType(Dash.RETRIEVE_MY_GROUPS)
        .map((action: Dash.RetrieveMyGroups) => action.payload)
        .exhaustMap(req =>

            this.groupService.list(req.request, req.token)
                .map(resp => {
                    const response = resp.json();
                    // GroupListResponse
                    return new Dash.MyGroupsRetrieved({
                        groups: response.list
                    });
                })
                .catch(error => of(new Dash.RetrievalFailure(error)))
        );

    constructor(private actions$: Actions,
                private groupService: GroupApiService,
                private router: Router) {
    }
}
