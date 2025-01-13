import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  private messageSubject = new BehaviorSubject<{ text: string, type: 'error' | 'success' } | null>(null);
  message$ = this.messageSubject.asObservable();

  showError(messageOrError: string | Error) {
    let errorMessage: string;

    if (messageOrError instanceof Error) {
      errorMessage = messageOrError.message;
    } else {
      errorMessage = messageOrError;
    }

    this.messageSubject.next({ text: errorMessage, type: 'error' });
  }

  showSuccess(message: string) {
    this.messageSubject.next({ text: message, type: 'success' });
  }

  clearMessage() {
    this.messageSubject.next(null);
  }
}
