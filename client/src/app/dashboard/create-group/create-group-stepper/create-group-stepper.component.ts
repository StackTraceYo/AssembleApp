import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CreateRequest} from '../../../group/group-api/request/create-request';
import {GroupApiService} from '../../../group/group-api/group-api.service';
import {MatDialog} from '@angular/material/';
import {CreateGroupSuccessDialogComponent} from '../create-group-success-dialog/create-group-success-dialog.component';
import {Category} from '../../../content/model/category';

@Component({
    selector: 'asm-create-group-stepper',
    templateUrl: './create-group-stepper.component.html',
    styleUrls: ['./create-group-stepper.component.scss']
})
export class CreateGroupStepperComponent implements OnInit {

    @Input() categories: Category;
    @Output() onCreateFinished = new EventEmitter<string>();

    basicInfo: FormGroup;
    category: FormGroup;
    additionalInfo: FormGroup;

    constructor(private _formBuilder: FormBuilder, private groupApiService: GroupApiService, public dialog: MatDialog) {
    }

    onCategorySelected(category: Category) {
        this.category.value.categoryName = category.categoryName;
    }

    ngOnInit() {
        this.basicInfo = this._formBuilder.group({
            groupName: ['', Validators.required]
        });
        this.category = this._formBuilder.group({
            categoryName: ['', Validators.required]
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
                this.showSuccess();
            },
            err => {
                console.log(err);
            });
    }

    showSuccess(): void {
        const dialogRef = this.dialog.open(CreateGroupSuccessDialogComponent, {
            width: '250px',
            data: {title: 'Congrats'}
        });

        dialogRef.afterClosed().subscribe(result => {
            this.onCreateFinished.emit('create');
        });
    }
}
