export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/',
};

export const REGISTER_PATH = environment.apiUrl + 'api/auth/register';
export const LOGIN_PATH = environment.apiUrl + 'api/auth/login';
export const POST_PATH = environment.apiUrl + 'api/post';
export const GROUP_PATH = environment.apiUrl + 'api/group';
export const VOTE_PATH = environment.apiUrl + 'api/vote';
export const COMMENT_PATH = environment.apiUrl + 'api/comment';
export const COMMENT_POST_PATH = environment.apiUrl + 'api/comment/post';
