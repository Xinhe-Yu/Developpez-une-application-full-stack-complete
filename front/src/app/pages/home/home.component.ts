import { AsyncPipe, DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { RouterLink } from '@angular/router';
import { catchError, map, Observable, of, switchMap } from 'rxjs';
import { Articles } from 'src/app/interfaces/articles.interface';
import { ArticleService } from 'src/app/services/article.service';
import { SessionService } from 'src/app/services/session.service';
import { ToastService } from 'src/app/services/toast.service';
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
  public sortOrder: 'asc' | 'desc' = 'desc';


  constructor(
    private sessionService: SessionService,
    private articleService: ArticleService,
    private toastService: ToastService
  ) { }

  ngOnInit() {
    this.$isLogged = this.sessionService.$isLogged();
    this.$articles = this.$isLogged.pipe(
      switchMap(isLogged => {
        if (isLogged) {
          return this.articleService.all().pipe(
            catchError(error => {
              this.toastService.showError(error);
              return of(null);
            })
          );
        } else {
          return of(null);
        }
      })
    );
  }

  toggleSort() {
    this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc';
    this.$articles = this.fetchSortedArticles();
  }

  private fetchSortedArticles(): Observable<Articles | null> {
    return this.articleService.all().pipe(
      map(articles => {
        if (articles && articles.data) {
          articles.data.sort((a, b) => {
            const dateA = new Date(a.createdAt).getTime();
            const dateB = new Date(b.createdAt).getTime();
            return this.sortOrder === 'asc' ? dateA - dateB : dateB - dateA;
          });
        }
        return articles;
      }),
      catchError(error => {
        this.toastService.showError(error);
        return of(null)
      })
    );
  }
}
