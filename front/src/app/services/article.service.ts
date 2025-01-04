import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Articles } from "../interfaces/articles.interface";
import { Article } from "../interfaces/article.interface";
import { Comments } from "../interfaces/comments.interface";

@Injectable({ providedIn: 'root' })
export class ArticleService {
  private pathService = '/api/articles';
  private httpClient = inject(HttpClient);

  public all(): Observable<Articles> {
    return this.httpClient.get<Articles>(`${this.pathService}`);
  }

  public detail(id: number): Observable<Article> {
    return this.httpClient.get<Article>(`${this.pathService}/${id}`);
  }

  public create(form: FormData): Observable<Response> {
    return this.httpClient.post<Response>(`${this.pathService}`, form);
  }

  public getComments(id: number): Observable<Comments> {
    return this.httpClient.get<Comments>(`${this.pathService}/${id}/comments`);
  }

  public createComment(id: number, form: FormData): Observable<Response> {
    return this.httpClient.post<Response>(`${this.pathService}/${id}/comments`, form);
  }
}
