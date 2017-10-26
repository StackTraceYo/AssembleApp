import {Component, OnInit} from '@angular/core';
import * as fromCreate from '../../reducers/create-group-reducers';
import {CreateGroupForm} from '../../reducers/create-group-reducers';
import * as fromAuth from '../../../user-auth/reducers/reducers';
import {Store} from '@ngrx/store';
import {Observable} from "rxjs/Observable";
import {FormGroupState} from "ngrx-forms";


@Component({
    selector: 'asm-create-group-review',
    templateUrl: './create-group-review.component.html',
    styleUrls: ['./create-group-review.component.scss']
})
export class CreateGroupReviewComponent implements OnInit {

    review$: Observable<FormGroupState<CreateGroupForm>>;

    constructor(private store: Store<fromCreate.State>, private authStore: Store<fromAuth.State>) {
    }

    ngOnInit() {
        this.review$ = this.store.select(s => s.createGroup)
    }

}
