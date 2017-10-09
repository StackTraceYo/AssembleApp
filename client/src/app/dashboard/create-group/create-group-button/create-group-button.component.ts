import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
    selector: 'asm-create-group-button',
    templateUrl: './create-group-button.component.html',
    styleUrls: ['./create-group-button.component.scss']
})
export class CreateGroupButtonComponent implements OnInit {

    @Output() onCreateClicked = new EventEmitter<string>();

    constructor() {
    }

    ngOnInit() {
    }

    create(): void {
        this.onCreateClicked.emit('createGroup');
    }

}
