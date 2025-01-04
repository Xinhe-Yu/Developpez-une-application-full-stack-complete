import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
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
    return this.httpClient.post<Response>(`${this.pathService}/${id}/subscribe`, {});
  }

  public unsubscribe(id: number): Observable<Response> {
    return this.httpClient.delete<Response>(`${this.pathService}/${id}/subscribe`, {});
  }
}
