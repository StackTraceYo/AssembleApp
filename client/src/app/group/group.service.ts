import {Injectable} from '@angular/core';
import {GroupApiService} from './group-api/group-api.service';
import {GroupListRequest} from './group-api/request/group-list-request';
import {AssembleGroup} from './model/assemble-group';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

@Injectable()
export class GroupService {

    private myGroups: AssembleGroup[] = [];
    private $myGroups: BehaviorSubject<AssembleGroup[]> = new BehaviorSubject<AssembleGroup[]>(this.myGroups);


    constructor(private groupApiService: GroupApiService) {
        this.listGroups();
    }

    listGroups() {

        const listOp = this.groupApiService.list(new GroupListRequest());

        listOp.subscribe(
            groups => {
                if (groups.list.length > 0) {
                    this.myGroups = groups.list.map(group => {
                        return new AssembleGroup(group.groupId);
                    });
                } else {
                    this.myGroups = [];
                }
                this.$myGroups.next(this.myGroups);
            },
            err => {
                console.log(err);
                this.myGroups = [];
                this.$myGroups.next(this.myGroups);
            });
    }

    getMyGroups() {
        return this.$myGroups.asObservable();
    }
}
