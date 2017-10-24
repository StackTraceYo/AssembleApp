import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'asm-create-group-button',
    templateUrl: './create-group-button.component.html',
    styleUrls: ['./create-group-button.component.scss']
})
export class CreateGroupButtonComponent implements OnInit {


    constructor() {
    }

    ngOnInit() {
    }

    create(): void {
        // this.onCreateClicked.emit('Create A Group');
    }

}
