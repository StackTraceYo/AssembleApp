import {Component, Input} from '@angular/core';
import {AssembleGroup} from '../../group/model/assemble-group';

@Component({
    selector: 'asm-dashboard-my-groups',
    templateUrl: './dashboard-my-groups.component.html',
    styleUrls: ['./dashboard-my-groups.component.scss']
})
export class DashboardMyGroupsComponent {

    @Input() groups: AssembleGroup[];

    constructor() {
    }
}
