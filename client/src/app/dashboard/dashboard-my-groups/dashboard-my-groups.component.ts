import {Component} from '@angular/core';
import {Store} from '@ngrx/store';
import * as fromDash from '../reducers/dash-reducers';
import {selectMyGroups} from '../reducers/reducers';

@Component({
    selector: 'asm-dashboard-my-groups',
    templateUrl: './dashboard-my-groups.component.html',
    styleUrls: ['./dashboard-my-groups.component.scss']
})
export class DashboardMyGroupsComponent {

    groups$ = this.store.select(selectMyGroups);

    constructor(private store: Store<fromDash.State>) {
    }
}
