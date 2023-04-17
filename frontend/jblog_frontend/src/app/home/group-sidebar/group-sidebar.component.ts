import { Component } from '@angular/core';
import { GroupDto } from 'src/app/services/dto/group.dto';
import { GroupService } from 'src/app/services/group.service';
import { tap, catchError, of } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-group-sidebar',
  templateUrl: './group-sidebar.component.html',
  styleUrls: ['./group-sidebar.component.css'],
})
export class GroupSidebarComponent {
  groups: Array<GroupDto> = [];
  viewAll: boolean = false;

  constructor(
    private groupService: GroupService,
    private toastr: ToastrService
  ) {
    this.groupService
      .getAllGroups()
      .pipe(
        tap((group) => {
          if (group.length >= 5) {
            this.groups = group.splice(0, 5);
            this.viewAll = true;
          } else {
            this.groups = group;
          }
        }),
        catchError((error) => {
          this.toastr.error(error.error.detail);
          return of(error);
        })
      )
      .subscribe();
  }
}
