import { Location } from '@angular/common';
import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { tap } from 'rxjs';
import { AuthService } from 'src/app/auth/services/auth.service';
import { UtilsService } from 'src/app/services/utils.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnChanges {
  isLoggedIn: boolean = false;
  username: string = '';
  isDropdownOpen = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private utils: UtilsService
  ) {
    this.authService
      .getUserName()
      .pipe(
        tap((data) => {
          this.username = data.username;
          this.isLoggedIn = this.username && this.username != '' ? true : false;
        })
      )
      .subscribe();
  }
  ngOnChanges(changes: SimpleChanges): void {
    this.utils.refreshPage();
  }

  logout() {
    this.authService.logout();
    this.utils.refreshPage();
  }
}
