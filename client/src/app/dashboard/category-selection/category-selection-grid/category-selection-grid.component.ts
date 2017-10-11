import {Component, Input, OnInit, Output, EventEmitter} from '@angular/core';
import {Category} from '../../../content/model/category';
import {ContentService} from '../../../content/content-api/content.service';
import {MatStepper} from '@angular/material';

@Component({
    selector: 'asm-category-selection-grid',
    templateUrl: './category-selection-grid.component.html',
    styleUrls: ['./category-selection-grid.component.scss']
})
export class CategorySelectionGridComponent implements OnInit {

    @Input() stepper: MatStepper;
    @Output() onCategorySelected = new EventEmitter<Category>();
    categories: Category;

    constructor(private contentService: ContentService) {
    }

    traverse(category: Category) {

        if (!category.isFinal) {
            this.categories = category;
        } else {
            this.onCategorySelected.emit(category);
            this.stepper.next();
        }
    }

    ngOnInit() {
        this.contentService.getCategories()
            .subscribe(c => {
                this.categories = c;
            });
    }

}
