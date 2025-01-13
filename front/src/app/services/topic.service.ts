import { inject, Injectable } from "@angular/core";
import { catchError, Observable, tap, throwError } from "rxjs";
import { Topic } from "../interfaces/topic.interface";
import { HttpClient } from "@angular/common/http";
import { ToastService } from "./toast.service";
import { Response } from "../interfaces/response.interface";

@Injectable({ providedIn: 'root' })
export class TopicService {
  private pathService = '/api/topics';
  private httpClient = inject(HttpClient);
  private toastService = inject(ToastService);

  public all(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}`);
  }

  public subscribe(id: number): Observable<Response> {
    return this.httpClient.post<Response>(`${this.pathService}/${id}/subscribe`, {}).pipe(
      tap(response => this.toastService.showSuccess(`${response.message}`)),
      catchError(error => {
        this.toastService.showError(`Error subscribing to topic ${id}`);
        return throwError(() => error);
      })
    );
  }

  public unsubscribe(id: number): Observable<Response> {
    return this.httpClient.delete<Response>(`${this.pathService}/${id}/subscribe`, {}).pipe(
      tap(response => this.toastService.showSuccess(`${response.message}`)),
      catchError(error => {
        this.toastService.showError(`Error unsubscribing to topic ${id}`);
        return throwError(() => error);
      })
    );
  }

  public getSubscriptions(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}/subs`);
  }
}
