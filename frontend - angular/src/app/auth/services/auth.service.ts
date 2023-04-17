import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
<<<<<<< HEAD:frontend - angular/src/app/auth/services/auth.service.ts
import { LOGIN_PATH, MY_PROFILE_PATH, REGISTER_PATH } from 'env.prod';
import { LoginReqPayload } from '../dtos/login-req.payload';
import { RegisterReqPayload } from '../dtos/register-req.payload';
import { AuthResponse } from '../dtos/auth-response';
import { map } from 'rxjs/operators';
=======
import { LOGIN_PATH, REGISTER_PATH } from 'env.prod';
import { LoginReqPayload } from '../payloads/login-req.payload';
import { RegisterReqPayload } from '../payloads/register-req.payload';
import { AuthResponse } from '../payloads/auth-response';
import { map, catchError } from 'rxjs/operators';
>>>>>>> parent of 053d69ea (complete frontend):frontend/jblog_frontend/src/app/auth/services/auth.service.ts
import { LocalStorageService } from 'ngx-webstorage';
import { UserDto } from 'src/app/services/dto/user.dto';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private httpClient: HttpClient,
    private localStorage: LocalStorageService
  ) {}

  register(registerReqPayload: RegisterReqPayload): Observable<any> {
    return this.httpClient
      .post<AuthResponse>(REGISTER_PATH, registerReqPayload)
      .pipe(
        map((data) => {
          this.storeToken(data);

          return data.detail;
        })
      );
  }

  login(loginReqPayload: LoginReqPayload) {
    return this.httpClient.post<AuthResponse>(LOGIN_PATH, loginReqPayload).pipe(
      map((data) => {
        this.storeToken(data);
        return data.detail;
      })
    );
  }

  logout() {
    this.localStorage.clear('auth-token');
  }

  storeToken(data: AuthResponse) {
    this.localStorage.store('auth-token', data.token);
    // this.localStorage.store('message', data.detail);
  }
<<<<<<< HEAD:frontend - angular/src/app/auth/services/auth.service.ts

  getToken() {
    return this.localStorage.retrieve('auth-token');
  }

  isLoggedIn() {}
  getUserName(): Observable<UserDto> {
    return this.httpClient.get<UserDto>(MY_PROFILE_PATH);
  }
=======
>>>>>>> parent of 053d69ea (complete frontend):frontend/jblog_frontend/src/app/auth/services/auth.service.ts
}
