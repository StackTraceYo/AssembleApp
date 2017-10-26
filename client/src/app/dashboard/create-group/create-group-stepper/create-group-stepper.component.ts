import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {MatDialog, MatStepper} from '@angular/material/';
import {CreateGroupSuccessDialogComponent} from '../create-group-success-dialog/create-group-success-dialog.component';
import {Store} from '@ngrx/store';
import * as fromCreate from '../../reducers/create-group-reducers';
import {CreateGroupForm} from '../../reducers/create-group-reducers';
import * as dash from '../../reducers/reducers';
import * as fromAuth from '../../../user-auth/reducers/reducers';
import {Observable} from 'rxjs/Observable';
import {FormGroupState} from 'ngrx-forms';

@Component({
    selector: 'asm-create-group-stepper',
    templateUrl: './create-group-stepper.component.html',
    styleUrls: ['./create-group-stepper.component.scss']
})
export class CreateGroupStepperComponent implements OnInit {

    createGroupForm$: Observable<FormGroupState<CreateGroupForm>>;
    @Output() onCreateFinished = new EventEmitter<string>();
    @ViewChild(MatStepper) stepper: MatStepper;


    constructor(public dialog: MatDialog, private store: Store<fromCreate.State>, private authStore: Store<fromAuth.State>) {
    }

    ngOnInit() {
        this.createGroupForm$ = this.store.select(dash.selectCreateGroup);
    }

    create() {
        // this.authStore.select(selectAuthStatusState).subscribe(state => {
        //         if (state.authenticated) {
        //             const request = new CreateRequest();
        //             request.groupName = this.basicInfo.category.groupName;
        //             request.categoryName = this.category.category.categoryName;
        //             this.store.dispatch(new CreateGroup({request: request, token: state.token}));
        //         }
        //     }
        // );
    }

    next() {
        this.stepper.next();
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
