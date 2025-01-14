import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  private messageSubject = new BehaviorSubject<{ text: string, type: 'error' | 'success' } | null>(null);
  message$ = this.messageSubject.asObservable();

  showError(messageOrError: string | Error | HttpErrorResponse) {
    let errorMessage: string;

    if (messageOrError instanceof HttpErrorResponse) {
      if (messageOrError.error instanceof ErrorEvent) {
        errorMessage = "Veuillez vérifier votre connexion internet";
      } else {
        errorMessage = this.extractErrorMessage(messageOrError);
      }
    } else if (messageOrError instanceof Error) {
      errorMessage = messageOrError.message;
    } else {
      errorMessage = messageOrError;
    }

    this.messageSubject.next({ text: errorMessage, type: 'error' });
  }

  private extractErrorMessage(error: HttpErrorResponse): string {
    if (typeof error.error === 'object' && error.error !== null && 'error' in error.error) {
      return error.error.error;
    }
    return error.statusText || "Une erreur s'est produite";
  }

  showSuccess(message: string) {
    this.messageSubject.next({ text: message, type: 'success' });
  }

  clearMessage() {
    this.messageSubject.next(null);
  }
}
