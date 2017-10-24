import {Component, Input, OnInit} from '@angular/core';
import {Category} from '../../../content/model/category';
import {MatStepper} from '@angular/material';

@Component({
    selector: 'asm-category-selection-grid',
    templateUrl: './category-selection-grid.component.html',
    styleUrls: ['./category-selection-grid.component.scss']
})
export class CategorySelectionGridComponent implements OnInit {

    @Input() stepper: MatStepper;
    @Input() categories: Category;


    constructor() {
    }

    traverse(category: Category) {

        if (!category.isFinal) {
            this.categories = category;
        } else {
            this.stepper.next();
        }
    }

    ngOnInit() {
    }

}
