import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-dialogue-box',
  templateUrl: './dialogue-box.component.html',
  styleUrls: ['./dialogue-box.component.css'],
})
export class DialogueBoxComponent {
  title: string = 'Warning';
  message: string = 'Are you sure You want to proceed with the operation?';
  @Output() confirmed = new EventEmitter<boolean>();

  onConfirm() {
    this.confirmed.emit(true);
  }

  onCancel() {
    this.confirmed.emit(false);
  }
}
