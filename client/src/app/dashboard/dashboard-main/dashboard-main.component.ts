import {Component, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import * as fromDash from '../reducers/dash-reducers';
import * as dash from '../reducers/reducers';
import * as fromAuth from '../../user-auth/reducers/reducers';
import {selectAuthStatusState} from '../../user-auth/reducers/reducers';
import {RetrieveMyGroups} from '../actions/dashboard-actions';
import {GroupListRequest} from '../../group/group-api/request/group-list-request';

@Component({
    selector: 'asm-dashboard-main',
    templateUrl: './dashboard-main.component.html',
    styleUrls: ['./dashboard-main.component.scss']
})
export class DashboardMainComponent implements OnInit {

    showBackToDash$ = this.store.select(dash.selectShowBack);
    currentView$ = this.store.select(dash.selectCurrentView);
    viewName$ = this.store.select(dash.selectViewName);


    constructor(private store: Store<fromDash.State>, private authStore: Store<fromAuth.State>) {
    }

    ngOnInit() {
        this.authStore.select(selectAuthStatusState).subscribe(state => {
            if (state.authenticated) {
                this.store.dispatch(new RetrieveMyGroups({request: new GroupListRequest(), token: state.token}));
            }
            }
        );
    }


}
