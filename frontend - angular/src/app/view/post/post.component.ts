import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { catchError, of, tap } from 'rxjs';
import { CommentService } from 'src/app/services/comment.service';
import { CommentDto } from 'src/app/services/dto/comment.dto';
import { PostDto } from 'src/app/services/dto/post.dto';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css'],
})
export class PostComponent {
  postId: string;
  post: PostDto = new PostDto();

  commentForm: FormGroup; // remove this
  commentDto: CommentDto = new CommentDto(); // remove this
  comments: CommentDto[] = []; // remove this

  constructor(
    private postService: PostService,
    private activateRoute: ActivatedRoute,
    private toastr: ToastrService,
    private commentService: CommentService // remove this code
  ) {
    this.postId = this.activateRoute.snapshot.params['id'];

    this.commentForm = new FormGroup({
      text: new FormControl('', Validators.required),
    });
    this.getPostById();
    this.getAllComments();
  }

  postComment() {
    this.commentDto.text = this.commentForm.get('text')?.value;
    this.commentDto.postId = this.post.id;

    this.commentService
      .createComment(this.commentDto)
      .pipe(
        tap(() => {
          this.commentForm.get('text')?.setValue('');
          this.getAllComments();
        }),
        catchError((error) => {
          console.log('here');

          console.log(error);
          return of(error);
        })
      )
      .subscribe();
  }

  private getPostById() {
    this.postService
      .getPostById(this.postId)
      .pipe(
        tap((data) => {
          this.post = data;
        }),
        catchError((error) => {
          console.log(error);
          this.toastr.error(error.error.detail);
          return of(error);
        })
      )
      .subscribe();
  }

  private getAllComments() {
    this.commentService
      .getCommentsByPost(this.postId)
      .pipe(
        tap((data) => {
          this.comments = data;
        }),
        catchError((error) => {
          return of(error);
        })
      )
      .subscribe();
  }
}
