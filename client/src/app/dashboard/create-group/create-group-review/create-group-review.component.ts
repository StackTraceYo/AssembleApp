import {Component, OnInit} from '@angular/core';
import * as fromCreate from '../../reducers/create-group-reducers';
import * as fromAuth from '../../../user-auth/reducers/reducers';
import {Store} from '@ngrx/store';
import {selectCreateGroup} from "../../reducers/reducers";


@Component({
    selector: 'asm-create-group-review',
    templateUrl: './create-group-review.component.html',
    styleUrls: ['./create-group-review.component.scss']
})
export class CreateGroupReviewComponent implements OnInit {

    review$ = this.store.select(selectCreateGroup);

    constructor(private store: Store<fromCreate.State>, private authStore: Store<fromAuth.State>) {
    }

    ngOnInit() {
        // this.review$ = Observable.combineLatest(
        //     this.store.select(fromCreate.getCreateForm),
        //     this.store.select(fromCreate.getSelectedCategoryStatus),
        //     (form, stat) => {
        //         return {
        //             groupName: form.value.groupName,
        //             categoryName: stat.categoryName
        //         };
        //     }
        // );

    }

}
