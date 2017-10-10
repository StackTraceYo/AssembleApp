import {Component, OnInit} from '@angular/core';
import {GroupApiService} from '../../group/group-api/group-api.service';
import {GroupListRequest} from '../../group/group-api/request/group-list-request';
import {AssembleGroup} from '../../group/model/assemble-group';

@Component({
    selector: 'asm-dashboard-main',
    templateUrl: './dashboard-main.component.html',
    styleUrls: ['./dashboard-main.component.scss']
})
export class DashboardMainComponent implements OnInit {

    currentView = '';
    viewName = 'Dashboard';
    landing = true;
    myGroups: AssembleGroup[];


    constructor(private groupService: GroupApiService) {
    }

    onViewChange(viewName: string) {
        this.currentView = viewName;
        this.viewName = viewName;
        this.landing = viewName === 'Dashboard';
    }

    onGroupCreated(name: String) {
        this.backToDash();
    }

    backToDash() {
        this.landing = true;
        this.viewName = 'Dashboard';
        this.currentView = 'Dashboard';
    }

    ngOnInit() {
        const listOp = this.groupService.list(new GroupListRequest());

        listOp.subscribe(
            groups => {
                if (groups.list.length > 0) {
                    this.myGroups = groups.list.map(group => {
                        return new AssembleGroup(group.groupId);
                    });
                    this.onViewChange('My Groups');
                } else {
                    this.backToDash();
                }
            },
            err => {
                console.log(err);
            });
    }

}
