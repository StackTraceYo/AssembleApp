import {Component, Input, OnInit} from '@angular/core';
import {Category} from '../../../content/model/category';
import {MatStepper} from '@angular/material';
import {Store} from '@ngrx/store';
import * as fromCreate from '../../reducers/create-group-reducers';
import {FORM_ID} from '../../reducers/create-group-reducers';
import * as dash from '../../reducers/reducers';
import {UpdateCategory} from '../../actions/dashboard-actions';
import {SetValueAction} from 'ngrx-forms';

@Component({
    selector: 'asm-category-selection-grid',
    templateUrl: './category-selection-grid.component.html',
    styleUrls: ['./category-selection-grid.component.scss']
})
export class CategorySelectionGridComponent implements OnInit {

    @Input() stepper: MatStepper;
    categories$ = this.store.select(dash.selectCategories);
    selectedCategoryData$ = this.store.select(dash.selectCurrentCategoryStatus);


    constructor(private store: Store<fromCreate.State>) {
    }

    traverse(category: Category) {
        this.selectedCategoryData$.take(1).subscribe(status => {
            if (status.isFinal) {
                this.stepper.next();
            } else {
                this.store.dispatch(new UpdateCategory({
                    category: category,
                    categoryStatus: {
                        isFinal: category.isFinal,
                        categoryName: category.categoryName
                    }
                }));
            }
        });
    }

    back() {
        this.stepper.previous();
    }

    next() {
        this.stepper.next();
    }

    ngOnInit() {
        this.selectedCategoryData$.subscribe(statusChange => {
            this.store.dispatch(new SetValueAction(FORM_ID + '.categoryName', statusChange.categoryName));
        });

    }

}
