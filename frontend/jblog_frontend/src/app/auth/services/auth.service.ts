import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { LOGIN_PATH, REGISTER_PATH } from 'env.prod';
import { LoginReqPayload } from '../payloads/login-req.payload';
import { RegisterReqPayload } from '../payloads/register-req.payload';
import { AuthResponse } from '../payloads/auth-response';
import { map, catchError } from 'rxjs/operators';
import { LocalStorageService } from 'ngx-webstorage';

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
        console.log(data);
        return data.detail;
      })
    );
  }

  storeToken(data: AuthResponse) {
    this.localStorage.store('auth-token', data.token);
    // this.localStorage.store('message', data.detail);
  }
}
