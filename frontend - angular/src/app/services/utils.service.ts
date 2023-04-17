import { Injectable } from '@angular/core';
import { Location } from '@angular/common';
import { environment } from 'env.prod';

@Injectable({
  providedIn: 'root',
})
export class UtilsService {
  constructor(private location: Location) {}

  refreshPage() {
    this.location.replaceState(this.location.path(true));
    window.location.reload();
  }

  goToPage(url: string = environment.uiUrl) {
    this.location.replaceState(this.location.path(true));
    window.location.assign(url);
  }
}
