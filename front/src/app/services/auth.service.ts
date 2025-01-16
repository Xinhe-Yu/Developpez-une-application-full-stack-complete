import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { catchError, Observable, tap, throwError } from "rxjs";
import { LoginRequest } from "../interfaces/auth/loginRequest.interface";
import { RegisterRequest } from "../interfaces/auth/registerRequest.interface";
import { Jwt } from "../interfaces/auth/jwt.interface";
import { Session } from "../interfaces/auth/session.interface";
import { UpdateRequest } from "../interfaces/auth/updateRequest.interface";
import { ToastService } from "./toast.service";

@Injectable({ providedIn: 'root' })
export class AuthService {
  private pathService = '/api/auth';
  private httpClient = inject(HttpClient);
  private toastService = inject(ToastService);

  public login(loginRequest: LoginRequest): Observable<Jwt> {
    return this.httpClient.post<Jwt>(`${this.pathService}/login`, loginRequest).pipe(
      catchError(error => {
        this.toastService.showError(error);
        return throwError(() => error);
      })
    );
  }

  public register(registerRequest: RegisterRequest): Observable<Jwt> {
    return this.httpClient.post<Jwt>(`${this.pathService}/register`, registerRequest).pipe(
      catchError(error => {
        this.toastService.showError(error);
        return throwError(() => error);
      })
    );
  }

  public me(): Observable<Session> {
    return this.httpClient.get<Session>(`${this.pathService}/me`);
  }

  public update(updateRequest: UpdateRequest): Observable<Jwt> {
    return this.httpClient.put<Jwt>(`${this.pathService}/update`, updateRequest).pipe(
      tap(() => {
        this.toastService.showSuccess('Profil modifié avec succès');
      }),
      catchError(error => {
        this.toastService.showError(error);
        return throwError(() => error);
      })
    );
  }
}
