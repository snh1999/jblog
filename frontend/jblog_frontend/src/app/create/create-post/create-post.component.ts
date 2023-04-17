import { Component, ElementRef } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PostCategory, PostDto } from 'src/app/services/dto/post.dto';
import { PostService } from 'src/app/services/post.service';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css'],
})
export class CreatePostComponent {
  PostCategory = PostCategory;
  items: PostCategory[] = [
    PostCategory.ADVICE,
    PostCategory.LOVE,
    PostCategory.FAMILY,
    PostCategory.MONKEY,
    PostCategory.HEALTH,
    PostCategory.FRIENDSHIP,
    PostCategory.SPIRITUALITY,
    PostCategory.JOB,
    PostCategory.FUN,
    PostCategory.OTHER,
  ];
  showClearDialog = false;

  createPostForm: FormGroup;
  postPayload: PostDto;

  constructor(
    private router: Router,
    private postService: PostService,
    private toastr: ToastrService
  ) {
    this.createPostForm = new FormGroup({
      title: new FormControl('', Validators.required),
      url: new FormControl(''),
      description: new FormControl('', Validators.required),
      category: new FormControl(),
      summary: new FormControl(''),
    });
    this.postPayload = new PostDto();
  }

  createPost() {
    this.postPayload.title = this.createPostForm.get('title')?.value;
    this.postPayload.url = this.createPostForm.get('url')?.value;
    this.postPayload.description =
      this.createPostForm.get('description')?.value;
    this.postPayload.category = this.createPostForm.get('category')?.value;
    this.postPayload.summary = this.createPostForm.get('summary')?.value;

    this.postService
      .createNewPost(this.postPayload)
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

  discardPost() {
    this.router.navigateByUrl('/');
  }

  clearForm() {
    this.createPostForm.reset();
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
