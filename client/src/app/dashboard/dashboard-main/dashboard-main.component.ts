import {Component, OnInit} from '@angular/core';
import {AssembleGroup} from '../../group/model/assemble-group';
import {GroupService} from '../../group/group.service';
import {ContentService} from '../../content/content-api/content.service';
import {Category} from '../../content/model/category';

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
    categories: Category;


    constructor(private groupService: GroupService, private contentService: ContentService) {
    }

    onViewChange(viewName: string) {
        this.currentView = viewName;
        this.viewName = viewName;
        this.showBackToDash = viewName === 'Dashboard';
    }

    onGroupCreated(name: String) {
        this.onViewChange('Dashboard');
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
        this.contentService.getCategories()
            .subscribe(c => {
                this.categories = c;
            });
    }


}
