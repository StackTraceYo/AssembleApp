import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'asm-dashboard-main',
    templateUrl: './dashboard-main.component.html',
    styleUrls: ['./dashboard-main.component.scss']
})
export class DashboardMainComponent implements OnInit {

    currentView = 'myGroups';

    onViewChange(viewName: string) {
        console.log(viewName + ' recieved');
        this.currentView = viewName;
    }

    constructor() {
    }

    ngOnInit() {
    }

}
