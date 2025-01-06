import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { LoginRequest } from "../interfaces/auth/loginRequest.interface";
import { RegisterRequest } from "../interfaces/auth/registerRequest.interface";
import { Jwt } from "../interfaces/auth/jwt.interface";
import { Session } from "../interfaces/auth/session.interface";
import { UpdateRequest } from "../interfaces/auth/updateRequest.interface";

@Injectable({ providedIn: 'root' })
export class AuthService {
  private pathService = '/api/auth';
  private httpClient = inject(HttpClient);

  public login(loginRequest: LoginRequest): Observable<Jwt> {
    return this.httpClient.post<Jwt>(`${this.pathService}/login`, loginRequest);
  }

  public register(registerRequest: RegisterRequest): Observable<Jwt> {
    return this.httpClient.post<Jwt>(`${this.pathService}/register`, registerRequest);
  }

  public me(): Observable<Session> {
    return this.httpClient.get<Session>(`${this.pathService}/me`);
  }

  public update(updateRequest: UpdateRequest): Observable<Response> {
    return this.httpClient.put<Response>(`${this.pathService}/update`, updateRequest);
  }
}
