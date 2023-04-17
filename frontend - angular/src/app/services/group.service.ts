import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GROUP_PATH } from 'env.prod';
import { Observable } from 'rxjs';
import { GroupDto } from './dto/group.dto';

@Injectable({
  providedIn: 'root',
})
export class GroupService {
  constructor(private httpClient: HttpClient) {}

  getAllGroups(): Observable<Array<GroupDto>> {
    return this.httpClient.get<Array<GroupDto>>(GROUP_PATH);
  }

  createNewGroup(GroupDto: GroupDto): Observable<GroupDto> {
    return this.httpClient.post<GroupDto>(GROUP_PATH, GroupDto);
  }
}
