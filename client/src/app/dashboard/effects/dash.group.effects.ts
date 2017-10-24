import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/exhaustMap';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/take';
import {Injectable} from '@angular/core';
import {Actions, Effect} from '@ngrx/effects';
import {of} from 'rxjs/observable/of';
import * as Dash from '../actions/dashboard-actions';
import {GroupApiService} from '../../group/group-api/group-api.service';
import * as lo from 'lodash';
import {AssembleGroup} from '../../group/model/assemble-group';
import {AssembleApiGroup} from '../../group/group-api/assemble-api-group';
import {GroupListResponse} from '../../group/group-api/response/group-list-response';

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
                    const groups = response.list;
                    return lo.map(groups, function apiToModel(apiGroup: AssembleApiGroup) {
                        return new AssembleGroup(apiGroup);
                    });
                })
                .map(groups => {
                    return new Dash.MyGroupsRetrieved({
                        groups: groups
                    });
                })
                .catch(error => of(new Dash.RetrievalFailure(error)))
        );

    constructor(private actions$: Actions,
                private groupService: GroupApiService) {
    }
}
