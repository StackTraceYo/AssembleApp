import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {MatDialog, MatStepper} from '@angular/material/';
import {CreateGroupSuccessDialogComponent} from '../create-group-success-dialog/create-group-success-dialog.component';
import {Store} from '@ngrx/store';
import * as fromCreate from '../../reducers/create-group-reducers';
import {CreateGroupForm} from '../../reducers/create-group-reducers';
import * as dash from '../../reducers/reducers';
import {selectCreateGroup} from '../../reducers/reducers';
import * as fromAuth from '../../../user-auth/reducers/reducers';
import {selectAuthStatusState} from '../../../user-auth/reducers/reducers';
import {Observable} from 'rxjs/';
import {FormGroupState} from 'ngrx-forms';
import {CreateRequest} from '../../../group/group-api/request/create-request';
import {CreateGroup, CreationCompleted} from '../../actions/dashboard-actions';

@Component({
    selector: 'asm-create-group-stepper',
    templateUrl: './create-group-stepper.component.html',
    styleUrls: ['./create-group-stepper.component.scss']
})
export class CreateGroupStepperComponent implements OnInit {

    createGroupForm$: Observable<FormGroupState<CreateGroupForm>>;
    @Output() onCreateFinished = new EventEmitter<string>();
    @ViewChild(MatStepper) stepper: MatStepper;
    showDialog$ = this.store.select(dash.selectDialog);


    constructor(public dialog: MatDialog, private store: Store<fromCreate.State>, private authStore: Store<fromAuth.State>) {
    }

    ngOnInit() {
        this.createGroupForm$ = this.store.select(dash.selectCreateGroup);
        this.showDialog$.subscribe(show => {
            if (show) {
                this.showSuccess();
            }
        });
    }

    create() {
        Observable.combineLatest(
            this.store.select(selectCreateGroup),
            this.authStore.select(selectAuthStatusState),
            (form, auth) => {
                const create = new CreateRequest(form.value);
                return new CreateGroup({request: create, token: auth.token});
            }
        ).take(1).subscribe(action => this.store.dispatch(action));
    }

    next() {
        this.stepper.next();
    }

    back() {
        this.stepper.previous();
    }

    showSuccess(): void {
        const dialogRef = this.dialog.open(CreateGroupSuccessDialogComponent, {
            width: '250px',
            data: {title: 'Congrats'}
        });

        dialogRef.afterClosed().take(1).subscribe(result => {
            this.store.dispatch(new CreationCompleted({msg: ''}));
        });
    }
}
