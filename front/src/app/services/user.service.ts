import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { catchError, Observable, throwError } from "rxjs";
import { User } from "../interfaces/user.interface";
import { ToastService } from "./toast.service";

@Injectable({ providedIn: 'root' })
export class UserService {
  private pathService = '/api/user';
  private httpClient = inject(HttpClient);
  private toastService = inject(ToastService);

  public getProfile(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/profile`).pipe(
      catchError(error => {
        this.toastService.showError(error);
        return throwError(() => error);
      })
    );
  }
}
