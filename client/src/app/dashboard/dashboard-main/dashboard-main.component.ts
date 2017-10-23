import {Component, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import * as fromDash from '../reducers/reducers';
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

    currentView = 'My Groups';
    viewName = 'Dashboard';
    showBackToDash = true;

    constructor(private store: Store<fromDash.State>, private authStore: Store<fromAuth.State>) {
    }

    onViewChange(viewName: string) {
        this.currentView = viewName;
        this.viewName = viewName;
        this.showBackToDash = viewName === 'Dashboard';
    }

    onGroupCreated(name: String) {
        this.onViewChange('Dashboard');
    }

    backToDash() {
        this.showBackToDash = true;
        this.viewName = 'Dashboard';
        this.currentView = 'Dashboard';
    }

    ngOnInit() {
        this.authStore.select(selectAuthStatusState).subscribe(state => {
                this.store.dispatch(new RetrieveMyGroups({request: new GroupListRequest(), token: state.token}));
            }
        );
    }


}
