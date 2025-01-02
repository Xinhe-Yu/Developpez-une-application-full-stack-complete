import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { catchError, Observable, tap, throwError } from "rxjs";
import { User } from "../interfaces/user.interface";
import { LoginRequest } from "../interfaces/auth/loginRequest.interface";
import { RegisterRequest } from "../interfaces/auth/registerRequest.interface";
import { Jwt } from "../interfaces/auth/jwt.interface";

@Injectable({ providedIn: 'root' })
export class AuthService {
  public isLogged = false;
  public user: User | undefined;

  private pathService = 'api/auth';
  private httpClient = inject(HttpClient);

  public login(loginRequest: LoginRequest): Observable<Jwt> {
    return this.httpClient.post<Jwt>(`${this.pathService}/login`, loginRequest).pipe(
      tap(response => {
        localStorage.setItem('authToken', response.token);
      }),
      catchError(error => {
        console.error('Login failed', error);
        return throwError(() => new Error('Login failed. Please try again.'));
      })
    )
  }

  public register(registerRequest: RegisterRequest): Observable<Jwt> {
    return this.httpClient.post<Jwt>(`${this.pathService}/register`, registerRequest).pipe(
      tap(response => {
        localStorage.setItem('authToken', response.token);
      }),
      catchError(error => {
        console.error('Register failed', error);
        return throwError(() => new Error('Register failed. Please try again.'));
      })
    )
  }

  public logout(): void {
    localStorage.removeItem('authToken');
  }

  public isLoggedIn(): boolean {
    return !!localStorage.getItem('authToken');
  }
}
