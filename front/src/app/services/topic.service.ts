import { inject, Injectable } from "@angular/core";
import { catchError, Observable, tap, throwError } from "rxjs";
import { Topic } from "../interfaces/topic.interface";
import { HttpClient } from "@angular/common/http";

@Injectable({ providedIn: 'root' })
export class TopicService {
  private pathService = '/api/topics';
  private httpClient = inject(HttpClient);

  public all(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}`);
  }

  public subscribe(id: number): Observable<Response> {
    console.log(`TopicService: Subscribing to topic ${id}`);
    return this.httpClient.post<Response>(`${this.pathService}/${id}/subscribe`, {}).pipe(
      tap(response => console.log(`TopicService: Subscribed to topic ${id}`, response)),
      catchError(error => {
        console.error(`TopicService: Error subscribing to topic ${id}`, error);
        return throwError(() => error);
      })
    );
  }

  public unsubscribe(id: number): Observable<Response> {
    return this.httpClient.delete<Response>(`${this.pathService}/${id}/subscribe`, {});
  }

  public getSubscriptions(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}/subs`);
  }
}
