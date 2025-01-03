import { AsyncPipe } from '@angular/common';
import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { filter, map, Observable } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';

@Component({
  selector: 'app-navbar',
  imports: [
    RouterLink,
    AsyncPipe,
    MatIconModule,
    MatSidenavModule
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})

export class NavbarComponent {
  public $isLogged!: Observable<boolean>;
  public $notHomePage!: Observable<boolean>;
  isOffcanvasOpen = false;

  constructor(
    private sessionService: SessionService,
    private router: Router
  ) {
    this.$isLogged = this.sessionService.$isLogged();
    this.$notHomePage = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map(() => this.router.url !== '/')
    );
  }

  openOffcanvas(): void {
    this.isOffcanvasOpen = true;
  }

  closeOffcanvas(): void {
    this.isOffcanvasOpen = false;
  }
}
