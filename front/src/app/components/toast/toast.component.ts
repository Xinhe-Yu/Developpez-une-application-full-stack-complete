import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ErrorMessageService } from 'src/app/services/error-message.service';

@Component({
  selector: 'app-toast',
  imports: [],
  templateUrl: './toast.component.html',
  styleUrl: './toast.component.scss'
})
export class ToastComponent implements OnInit {
  errorMessage: string | null = null;
  private subscription: Subscription | undefined;

  constructor(private errorMessageService: ErrorMessageService) { }

  ngOnInit(): void {
    this.subscription = this.errorMessageService.error$.subscribe(message => {
      this.errorMessage = message;
      if (message) {
        setTimeout(() => this.errorMessageService.clearError(), 5000)
      }
    })
  }
  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
