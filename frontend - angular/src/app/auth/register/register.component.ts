import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { RegisterReqPayload } from '../payloads/register-req.payload';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  registerReqPayload: RegisterReqPayload;
  registerForm: FormGroup;

  constructor(
    private authService: AuthService,
    private toastr: ToastrService,
    private router: Router
  ) {
    this.registerForm = new FormGroup({});
    this.registerReqPayload = {
      username: '',
      email: '',
      password: '',
    };
  }

  ngOnInit(): void {
    this.registerForm = new FormGroup({
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required),
    });
  }

  register() {
    this.registerReqPayload.email = this.registerForm.get('email')?.value;
    this.registerReqPayload.username = this.registerForm.get('username')?.value;
    this.registerReqPayload.password = this.registerForm.get('password')?.value;

    this.authService
      .register(this.registerReqPayload)
      .pipe(
        tap((data) => {
          this.toastr.info(data);
          this.router.navigate(['/']);
        }),
        catchError((error) => {
          this.toastr.error(error.error.detail);
          return of(error);
        })
      )
      .subscribe();
  }
}
