import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CreateRequest} from '../../../group/group-api/request/create-request';
import {MatDialog} from '@angular/material/';
import {CreateGroupSuccessDialogComponent} from '../create-group-success-dialog/create-group-success-dialog.component';
import {Category} from '../../../content/model/category';
import {Store} from '@ngrx/store';
import * as fromCreate from '../../reducers/create-group-reducers';
import * as dash from '../../reducers/reducers';
import * as fromAuth from '../../../user-auth/reducers/reducers';
import {selectAuthStatusState} from '../../../user-auth/reducers/reducers';
import {CreateGroup} from '../../actions/dashboard-actions';

@Component({
    selector: 'asm-create-group-stepper',
    templateUrl: './create-group-stepper.component.html',
    styleUrls: ['./create-group-stepper.component.scss']
})
export class CreateGroupStepperComponent implements OnInit {

    categories$ = this.store.select(dash.selectCategories);
    @Output() onCreateFinished = new EventEmitter<string>();

    basicInfo: FormGroup;
    category: FormGroup;

    constructor(private _formBuilder: FormBuilder, public dialog: MatDialog,
                private store: Store<fromCreate.State>, private authStore: Store<fromAuth.State>) {
    }

    onCategorySelected(category: Category) {
        this.category.value.categoryName = category.categoryName;
    }

    ngOnInit() {
        this.basicInfo = this._formBuilder.group({
            groupName: ['', Validators.required]
        });
        this.category = this._formBuilder.group({
            categoryName: ['']
        });
    }

    create() {
        this.authStore.select(selectAuthStatusState).subscribe(state => {
                if (state.authenticated) {
                    const request = new CreateRequest();
                    request.groupName = this.basicInfo.value.groupName;
                    request.categoryName = this.category.value.categoryName;
                    this.store.dispatch(new CreateGroup({request: request, token: state.token}));
                }
            }
        );
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

    groupModel() {
        return {
            groupName: this.basicInfo.value.groupName,
            categoryName: this.category.value.categoryName
        };
    }
}
