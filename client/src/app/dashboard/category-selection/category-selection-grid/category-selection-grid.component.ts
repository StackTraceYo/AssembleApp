import {Component, OnInit} from '@angular/core';
import {Category} from '../../../content/model/category';
import {ContentService} from '../../../content/content-api/content.service';

@Component({
    selector: 'asm-category-selection-grid',
    templateUrl: './category-selection-grid.component.html',
    styleUrls: ['./category-selection-grid.component.scss']
})
export class CategorySelectionGridComponent implements OnInit {

    categories: Category;

    constructor(private contentService: ContentService) {
    }

    ngOnInit() {
        this.contentService.getCategories()
            .subscribe(c => {
                this.categories = c;
            });
    }

}
