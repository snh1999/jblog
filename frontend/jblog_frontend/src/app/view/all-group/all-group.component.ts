import { Component, OnInit } from '@angular/core';
import { GroupDto } from 'src/app/services/dto/group.dto';
import { GroupService } from 'src/app/services/group.service';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-all-group',
  templateUrl: './all-group.component.html',
  styleUrls: ['./all-group.component.css'],
})
export class AllGroupComponent implements OnInit {
  groups: Array<GroupDto> = [];
  constructor(
    private groupService: GroupService,
    private toastr: ToastrService
  ) {}

  ngOnInit() {
    this.groupService
      .getAllGroups()
      .pipe(
        tap((groups) => {
          this.groups = groups;
        }),
        catchError((error) => {
          this.toastr.error(error.error.detail);
          return of(error);
        })
      )
      .subscribe();
  }
}
