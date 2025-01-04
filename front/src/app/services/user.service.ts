import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { User } from "../interfaces/user.interface";
import { UpdateRequest } from "../interfaces/auth/updateRequest.interface";
import { Topic } from "../interfaces/topic.interface";

@Injectable({ providedIn: 'root' })
export class UserService {
  private pathService = '/api/user';
  private httpClient = inject(HttpClient);

  public getProfile(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/profile`);
  }

  public updateProfile(updateRequest: UpdateRequest): Observable<Response> {
    return this.httpClient.put<Response>(`${this.pathService}/profile`, updateRequest);
  }

  public getSubscriptions(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(`${this.pathService}/subs`);
  }
}
