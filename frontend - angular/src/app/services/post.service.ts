import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { POST_PATH } from 'env.prod';
import { Observable } from 'rxjs';
import { PostDto } from './dto/post.dto';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  constructor(private httpClient: HttpClient) {}

  getAllPosts(): Observable<Array<PostDto>> {
    return this.httpClient.get<Array<PostDto>>(POST_PATH);
  }

  createNewPost(postDto: PostDto): Observable<PostDto> {
    return this.httpClient.post<PostDto>(POST_PATH, postDto);
  }

  getPostById(id: string): Observable<PostDto> {
    return this.httpClient.get<PostDto>(POST_PATH + '/' + id);
  }
}
