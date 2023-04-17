export class CommentDto {
  id?: string;
  text: string = '';
  postId: string = '';
  authorName?: string;
  createdDate?: string;
}
