import {Component, OnInit} from '@angular/core';
import {CreateGroupModel} from '../model/create-group-model';

@Component({
    selector: 'asm-create-group-button',
    templateUrl: './create-group-button.component.html',
    styleUrls: ['./create-group-button.component.scss']
})
export class CreateGroupButtonComponent implements OnInit {

    createModel = new CreateGroupModel();


    constructor() {
    }

    ngOnInit() {
    }

    openDialog(): void {

    }

}
