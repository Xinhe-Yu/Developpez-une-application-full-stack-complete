import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { AuthService } from './services/auth.service';
import { SessionService } from './services/session.service';
import { Session } from './interfaces/auth/session.interface';
import { catchError, Observable, of, tap } from 'rxjs';
import { NavbarComponent } from './components/navbar/navbar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NavbarComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})

export class AppComponent implements OnInit {
  constructor(
    private authService: AuthService,
    private sessionService: SessionService) {
  }

  public ngOnInit(): void {
    this.autoConnect();
  }

  public autoConnect(): void {
    if (!localStorage.getItem('authToken')) {
      return;
    }
    this.authService.me().pipe(
      tap((user: Session) => this.sessionService.logIn(user)),
      catchError(() => {
        this.sessionService.logOut();
        return of(null);
      })
    ).subscribe();
  }
}
