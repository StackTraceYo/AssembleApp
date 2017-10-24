import {Component, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import * as fromDash from '../../reducers/dash-reducers';
import {CreateGroupStart} from '../../actions/dashboard-actions';

@Component({
    selector: 'asm-create-group-button',
    templateUrl: './create-group-button.component.html',
    styleUrls: ['./create-group-button.component.scss']
})
export class CreateGroupButtonComponent implements OnInit {


    constructor(private store: Store<fromDash.State>) {
    }

    ngOnInit() {
    }

    create(): void {
        this.store.dispatch(new CreateGroupStart());
    }

}
