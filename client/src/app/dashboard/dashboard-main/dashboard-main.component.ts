import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'asm-dashboard-main',
    templateUrl: './dashboard-main.component.html',
    styleUrls: ['./dashboard-main.component.scss']
})
export class DashboardMainComponent implements OnInit {

    currentView = '';
    viewName = 'Dashboard';
    landing = true;

    onViewChange(viewName: string) {
        this.currentView = viewName;
        this.viewName = viewName;
        this.landing = viewName === 'Dashboard';
    }

    backToDash() {
        this.landing = true;
        this.viewName = 'Dashboard';
        this.currentView = 'Dashboard';
    }

    constructor() {
    }

    ngOnInit() {
    }

}
