export class VotePayload {
  voteType?: VoteType;
  postId?: string;
}

export enum VoteType {
  UPVOTE,
  DOWNVOTE,
}
