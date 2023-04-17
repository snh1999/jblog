import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { COMMENT_PATH, COMMENT_POST_PATH } from 'env.prod';
import { Observable } from 'rxjs';
import { CommentDto } from './dto/comment.dto';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  constructor(private httpClient: HttpClient) {}

  getCommentsByPost(postId: string): Observable<CommentDto[]> {
    return this.httpClient.get<CommentDto[]>(COMMENT_POST_PATH + '/' + postId);
  }

  createComment(commentDto: CommentDto): Observable<CommentDto> {
    console.log(COMMENT_PATH);
    return this.httpClient.post<CommentDto>(COMMENT_PATH, commentDto);
  }
}
