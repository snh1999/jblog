import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { LoginReqPayload } from '../dtos/login-req.payload';
import { AuthService } from '../services/auth.service';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loginReqPayload: LoginReqPayload;
  isError: boolean;

  constructor(
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.loginForm = new FormGroup({});
    this.loginReqPayload = {
      email: '',
      password: '',
    };
    this.isError = false;
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      email: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
  }

  login() {
    this.loginReqPayload.email = this.loginForm.get('email')?.value;
    this.loginReqPayload.password = this.loginForm.get('password')?.value;

    this.authService.login(this.loginReqPayload).subscribe((data) => {
      console.log('here');
      console.log(data);
      console.log('Login Successful');
    });

    this.authService
      .login(this.loginReqPayload)
      .pipe(
        tap((data) => {
          this.toastr.success(data);
          this.router.navigate(['/']);
        }),
        catchError((error) => {
          this.isError = true;
          this.toastr.error(error.error.detail + '. Please Try again');
          return of(error);
        })
      )
      .subscribe();
  }
}
