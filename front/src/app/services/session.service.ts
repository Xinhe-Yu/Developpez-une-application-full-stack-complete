import { Injectable } from "@angular/core";
import { Session } from "../interfaces/auth/session.interface";
import { BehaviorSubject, Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class SessionService {
  public isLogged = false;
  public user: Session | undefined;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(user: Session): void {
    this.user = user;
    this.isLogged = true;
    this.isLoggedSubject.next(this.isLogged);
  }

  public logOut(): void {
    localStorage.removeItem('authToken');
    this.user = undefined;
    this.isLogged = false;
    this.isLoggedSubject.next(this.isLogged);
  }
}
