import {Component, OnInit} from '@angular/core';
import {AssembleGroup} from '../../group/model/assemble-group';
import {GroupService} from '../../group/group.service';

@Component({
    selector: 'asm-dashboard-main',
    templateUrl: './dashboard-main.component.html',
    styleUrls: ['./dashboard-main.component.scss']
})
export class DashboardMainComponent implements OnInit {

    currentView = 'Dashboard';
    viewName = 'Dashboard';
    showBackToDash = true;
    myGroups: AssembleGroup[];


    constructor(private groupService: GroupService) {
    }

    onViewChange(viewName: string) {
        this.currentView = viewName;
        this.viewName = viewName;
        this.showBackToDash = viewName === 'Dashboard';
    }

    onGroupCreated(name: String) {
        this.onViewChange('Dashboard');
        this.groupService.listGroups();
    }

    backToDash() {
        this.showBackToDash = true;
        this.viewName = 'Dashboard';
        this.currentView = 'Dashboard';
    }

    ngOnInit() {
        this.groupService.getMyGroups()
            .subscribe(groups => {
                this.myGroups = groups;
            });
    }


}
