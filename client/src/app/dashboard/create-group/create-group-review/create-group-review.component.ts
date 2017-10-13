import {Component, OnInit, Input} from '@angular/core';

@Component({
    selector: 'asm-create-group-review',
    templateUrl: './create-group-review.component.html',
    styleUrls: ['./create-group-review.component.scss']
})
export class CreateGroupReviewComponent implements OnInit {

    @Input() review: any;

    constructor() {
    }

    ngOnInit() {
    }

}
