import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CreateRequest} from '../../../group/group-api/request/create-request';
import {GroupApiService} from '../../../group/group-api/group-api.service';

@Component({
    selector: 'asm-create-group-stepper',
    templateUrl: './create-group-stepper.component.html',
    styleUrls: ['./create-group-stepper.component.scss']
})
export class CreateGroupStepperComponent implements OnInit {

    isLinear = false;
    basicInfo: FormGroup;
    additionalInfo: FormGroup;

    constructor(private _formBuilder: FormBuilder, private groupApiService: GroupApiService) {
    }

    ngOnInit() {
        this.basicInfo = this._formBuilder.group({
            groupName: ['', Validators.required]
        });
        this.additionalInfo = this._formBuilder.group({
            category: ['', Validators.required]
        });
    }

    create() {
        const request = new CreateRequest();
        request.groupName = this.basicInfo.value.groupName;
        const createOp = this.groupApiService.create(request);
        createOp.subscribe(
            created => {
                console.log(created.groupId);
            },
            err => {
                console.log(err);
            });
    }
}
