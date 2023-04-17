import { Component, Input, OnInit } from '@angular/core';
import { PostDto } from 'src/app/services/dto/post.dto';
import { faComments } from '@fortawesome/free-solid-svg-icons';
import { PostService } from 'src/app/services/post.service';
import { ToastrService } from 'ngx-toastr';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-post-box',
  templateUrl: './post-box.component.html',
  styleUrls: ['./post-box.component.css'],
})
export class PostBoxComponent implements OnInit {
  @Input() data: Array<PostDto> = [];
  posts$: Array<PostDto> = [];

  faComments = faComments;

  constructor(private postService: PostService, private toastr: ToastrService) {
    this.postService
      .getAllPosts()
      .pipe(
        tap((post) => {
          this.posts$ = post;
        }),
        catchError((error) => {
          this.toastr.error(error.error.detail);
          return of(error);
        })
      )
      .subscribe();
  }
  goToPost(a: any) {
    return a;
  }

  ngOnInit(): void {}
}
