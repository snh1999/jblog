export class PostDto {
  id: string = '';
  title?: string;
  url?: string;
  summary?: string;
  description?: string;
  voteCount?: number;
  authorName?: string;
  groupUrl?: string;
  category?: PostCategory;
  createdAt?: Date;
  commentCount?: number;
}

export enum PostCategory {
  ADVICE,
  LOVE,
  FAMILY,
  MONKEY,
  HEALTH,
  FRIENDSHIP,
  SPIRITUALITY,
  JOB,
  FUN,
  OTHER,
}
