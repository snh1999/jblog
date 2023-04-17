export class GroupDto {
  id?: number;
  name?: string;
  description?: string;
  url?: string;
  createdAt?: Date;
  groupType?: GroupType = GroupType.PUBLIC;
  ownerName?: string;
  memberCount?: number;
}

export enum GroupType {
  PUBLIC,
  PRIVATE,
}
