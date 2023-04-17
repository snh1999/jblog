import { Component, Input, OnInit } from '@angular/core';
import { PostDto } from 'src/app/services/dto/post.dto';
import { faArrowUp, faArrowDown } from '@fortawesome/free-solid-svg-icons';
import { VotePayload, VoteType } from './vote-dtos';
import { HttpClient } from '@angular/common/http';
import { VOTE_PATH } from 'env.prod';
import { catchError, Observable, of, tap } from 'rxjs';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-vote-button',
  templateUrl: './vote-button.component.html',
  styleUrls: ['./vote-button.component.css'],
})
export class VoteButtonComponent implements OnInit {
  @Input() post: PostDto = new PostDto();
  votePayload: VotePayload;
  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  upvoteColor?: string;
  downvoteColor?: string;
  tempVote: number = 0;

  constructor(
    private httpClient: HttpClient,
    private postService: PostService
  ) {
    this.votePayload = new VotePayload();
  }

  ngOnInit(): void {
    this.updateVoteDetails();
  }

  upvotePost() {
    this.votePayload.voteType = VoteType.UPVOTE;
    this.vote();
    this.downvoteColor = '';

    if (this.tempVote != 0) {
      this.upvoteColor = 'green';
    }
  }

  downvotePost() {
    this.votePayload.voteType = VoteType.DOWNVOTE;
    this.vote();

    this.upvoteColor = '';
    if (this.tempVote != 0) {
      this.downvoteColor = 'red';
    }
  }

  private vote() {
    this.votePayload.postId = this.post.id;
    console.log('data');

    this.voteRequest(this.votePayload)
      .pipe(
        tap((data) => {
          console.log(data);
          this.tempVote = data;
          this.updateVoteDetails();
        }),
        catchError((error) => {
          console.log(error);
          return of(error);
        })
      )
      .subscribe();
  }

  voteRequest(votePayload: VotePayload): Observable<any> {
    return this.httpClient.post(VOTE_PATH, votePayload);
  }

  private updateVoteDetails() {
    this.postService.getPostById(this.post.id).subscribe((post) => {
      this.post = post;
    });
  }
}
