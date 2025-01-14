import { AsyncPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Router, RouterLink } from '@angular/router';
import { BehaviorSubject, catchError, combineLatest, EMPTY, filter, finalize, map, tap } from 'rxjs';
import { Jwt } from 'src/app/interfaces/auth/jwt.interface';
import { Session } from 'src/app/interfaces/auth/session.interface';
import { UpdateRequest } from 'src/app/interfaces/auth/updateRequest.interface';
import { Topic } from 'src/app/interfaces/topic.interface';
import { AuthService } from 'src/app/services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-profile',
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatIconModule,
    AsyncPipe,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {
  public hideNewPassword = true;
  public hidePassword = true;
  private userDataSubject = new BehaviorSubject<{ user: Session | undefined, subscriptions: Topic[] }>({ user: undefined, subscriptions: [] });
  public $userData = this.userDataSubject.asObservable();

  public form: FormGroup = this.fb.group({
    username: [''],
    email: [''],
    newPassword: ['', [Validators.minLength(8), this.passwordValidator()]],
    password: ['', [Validators.required, Validators.minLength(8)]]

  });
  public isSubmitting = false;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private topicService: TopicService,
    private authService: AuthService,
    private router: Router,
  ) { }

  ngOnInit() {
    combineLatest({
      user: this.sessionService.$user(),
      subscriptions: this.topicService.getSubscriptions()
    }).pipe(
      filter(({ user }) => user !== undefined),
      map(({ user, subscriptions }) => ({
        user: user as Session,
        subscriptions
      }))
    ).subscribe(data => {
      this.userDataSubject.next(data);
      if (data.user) {
        this.form.patchValue({
          username: data.user.username,
          email: data.user.email
        });
      }
    });
  }

  ngOnDestroy(): void {
    this.userDataSubject.complete();
  }

  public submit(): void {
    if (this.form.invalid) {
      return;
    }

    this.isSubmitting = true;
    const updateRequest: UpdateRequest = {
      username: this.form.get('username')?.value || "",
      email: this.form.get('email')?.value || "",
      newPassword: this.form.get('newPassword')?.value || "",
      password: this.form.get('password')?.value || ""
    };

    this.authService.update(updateRequest).pipe(
      tap((response: Jwt) => {
        localStorage.setItem('authToken', response.token);
      }),
      catchError((error) => {
        console.error('Update failed', error);
        return EMPTY;
      }),
      finalize(() => {
        this.isSubmitting = false;
        this.form.get('newPassword')?.reset();
        this.form.get('password')?.reset();
      })
    ).subscribe();
  }

  public unsubscribe(topicId: number): void {
    this.topicService.unsubscribe(topicId).pipe(
      tap(() => {
        const currentData = this.userDataSubject.getValue();
        this.userDataSubject.next({
          ...currentData,
          subscriptions: currentData.subscriptions.filter(sub => sub.id !== topicId)
        });
      })
    ).subscribe();
  }

  public logOut(): void {
    this.sessionService.logOut();
    this.router.navigate(['']);
  }

  private passwordValidator(): ValidationErrors | null {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;

      if (!value) {
        return null;
      }

      const hasUpperCase = /[A-Z]/.test(value);
      const hasLowerCase = /[a-z]/.test(value);
      const hasNumeric = /[0-9]+/.test(value);
      const hasSpecialChar = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/.test(value);
      const passwordValid = hasUpperCase && hasLowerCase && hasNumeric && hasSpecialChar;
      return !passwordValid ? { passwordStrength: true } : null;
    }
  }
}
