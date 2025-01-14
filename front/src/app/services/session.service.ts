import { Injectable } from "@angular/core";
import { Session } from "../interfaces/auth/session.interface";
import { BehaviorSubject, Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class SessionService {
  private isLoggedSubject = new BehaviorSubject<boolean>(false);
  private userSubject = new BehaviorSubject<Session | undefined>(undefined);

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public $user(): Observable<Session | undefined> {
    return this.userSubject.asObservable();
  }

  public logIn(user: Session): void {
    this.userSubject.next(user);
    this.isLoggedSubject.next(true);
  }

  public logOut(): void {
    localStorage.removeItem('authToken');
    this.userSubject.next(undefined);
    this.isLoggedSubject.next(false);
  }
}
