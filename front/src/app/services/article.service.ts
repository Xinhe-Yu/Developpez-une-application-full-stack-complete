import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { catchError, Observable, tap, throwError } from "rxjs";
import { Articles } from "../interfaces/articles.interface";
import { Article } from "../interfaces/article.interface";
import { Comments } from "../interfaces/comments.interface";
import { Comment } from "../interfaces/comment.interface";
import { NewComment } from "../interfaces/form/newComment.interface";
import { NewArticle } from "../interfaces/form/newArticle.interface";
import { ToastService } from "./toast.service";

@Injectable({ providedIn: 'root' })
export class ArticleService {
  private pathService = '/api/articles';
  private httpClient = inject(HttpClient);
  private toastService = inject(ToastService);

  public all(): Observable<Articles> {
    return this.httpClient.get<Articles>(`${this.pathService}`).pipe(
      catchError(error => {
        this.toastService.showError(error);
        return throwError(() => error);
      })
    );
  }

  public detail(id: number): Observable<Article> {
    return this.httpClient.get<Article>(`${this.pathService}/${id}`).pipe(
      catchError(error => {
        this.toastService.showError(error);
        return throwError(() => error);
      })
    );
  }

  public create(article: NewArticle): Observable<Article> {
    return this.httpClient.post<Article>(`${this.pathService}`, article).pipe(
      tap(() => {
        this.toastService.showSuccess('Article ajouté avec succès');
      }),
      catchError(error => {
        this.toastService.showError(error);
        return throwError(() => error);
      })
    );
  }

  public getComments(id: number): Observable<Comments> {
    return this.httpClient.get<Comments>(`${this.pathService}/${id}/comments`).pipe(
      catchError(error => {
        this.toastService.showError(error);
        return throwError(() => error);
      })
    );
  }

  public createComment(id: number, comment: NewComment): Observable<Comment> {
    return this.httpClient.post<Comment>(`${this.pathService}/${id}/comments`, comment).pipe(
      tap(() => {
        this.toastService.showSuccess('Commentaire ajouté avec succès');
      }),
      catchError(error => {
        this.toastService.showError(error);
        return throwError(() => error);
      })
    );;
  }
}
