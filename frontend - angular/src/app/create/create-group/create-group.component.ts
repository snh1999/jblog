import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { GroupDto, GroupType } from 'src/app/services/dto/group.dto';
import { GroupService } from 'src/app/services/group.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.css'],
})
export class CreateGroupComponent {
  GroupType = GroupType;
  items: GroupType[] = [GroupType.PUBLIC, GroupType.PRIVATE];
  showClearDialog = false;

  createGroupForm: FormGroup;

  groupModel: GroupDto;

  constructor(
    private router: Router,
    private groupService: GroupService,
    private toastr: ToastrService
  ) {
    this.createGroupForm = new FormGroup({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
      url: new FormControl(null),
      groupType: new FormControl(''),
    });

    this.groupModel = new GroupDto();
  }

  createGroup() {
    this.groupModel.name = this.createGroupForm.get('name')?.value;
    this.groupModel.description =
      this.createGroupForm.get('description')?.value;
    this.groupModel.url = this.createGroupForm.get('url')?.value;
    this.groupModel.groupType = this.createGroupForm.get('groupType')?.value;

    this.groupService
      .createNewGroup(this.groupModel)
      .pipe(
        tap((group) => {
          this.toastr.success('Group Created');
          this.router.navigate(['/']);
        }),
        catchError((error) => {
          console.log(error);
          this.toastr.error(error.error.detail);
          return of(error);
        })
      )
      .subscribe();
  }

  clearForm() {
    this.createGroupForm.reset();
  }
  clear() {
    this.showClearDialog = true;
  }

  getClickEvent(result: boolean) {
    if (result) {
      this.clearForm();
    }
    this.showClearDialog = false;
  }
}
