import { AsyncPipe, DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { RouterLink } from '@angular/router';
import { Observable, of, switchMap } from 'rxjs';
import { Articles } from 'src/app/interfaces/articles.interface';
import { ArticleService } from 'src/app/services/article.service';
import { SessionService } from 'src/app/services/session.service';
@Component({
  selector: 'app-home',
  imports: [
    MatButtonModule,
    MatCardModule,
    RouterLink,
    AsyncPipe,
    DatePipe
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  public $isLogged!: Observable<boolean>;
  public $articles!: Observable<Articles | null>;

  constructor(
    private sessionService: SessionService,
    private articleService: ArticleService,
  ) { }

  ngOnInit() {
    this.$isLogged = this.sessionService.$isLogged();
    this.$articles = this.$isLogged.pipe(
      switchMap(isLogged => isLogged ? this.articleService.all() : of(null))
    );
  }
}
