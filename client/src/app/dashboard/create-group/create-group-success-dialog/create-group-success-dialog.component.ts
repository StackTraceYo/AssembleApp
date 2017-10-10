import {Component, Inject, OnInit} from '@angular/core';

import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';

@Component({
    selector: 'asm-create-group-success-dialog',
    templateUrl: './create-group-success-dialog.component.html',
    styleUrls: ['./create-group-success-dialog.component.scss']
})
export class CreateGroupSuccessDialogComponent implements OnInit {

    constructor(public dialogRef: MatDialogRef<CreateGroupSuccessDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public data: any) {
    }

    ngOnInit() {
    }

    onCloseClick(): void {
        this.dialogRef.close();
    }

}
