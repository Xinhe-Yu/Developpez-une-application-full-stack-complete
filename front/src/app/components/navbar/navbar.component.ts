import { AsyncPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterLink, RouterModule } from '@angular/router';
import { combineLatest, filter, map, Observable, startWith } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';

@Component({
  selector: 'app-navbar',
  imports: [
    RouterLink,
    AsyncPipe,
    MatIconModule,
    MatSidenavModule,
    RouterModule
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})

export class NavbarComponent implements OnInit {
  public $navState!: Observable<{ isLogged: boolean, isArticlePage: boolean, notHomePage: boolean }>;
  isOffcanvasOpen = false;

  constructor(
    private sessionService: SessionService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const $isLogged = this.sessionService.$isLogged().pipe(startWith(false));
    const $isArticlePage = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map(() => this.router.url.startsWith('/articles')),
      startWith(false)
    );
    const $notHomePage = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map(() => this.router.url !== '/'),
      startWith(false)
    );

    this.$navState = combineLatest({
      isLogged: $isLogged,
      isArticlePage: $isArticlePage,
      notHomePage: $notHomePage
    });
  }

  openOffcanvas(): void {
    this.isOffcanvasOpen = true;
  }

  closeOffcanvas(): void {
    this.isOffcanvasOpen = false;
  }
}
