import { AsyncPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCard, MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Router } from '@angular/router';
import { BehaviorSubject, catchError, combineLatest, EMPTY, filter, finalize, forkJoin, map, Observable, tap } from 'rxjs';
import { Jwt } from 'src/app/interfaces/auth/jwt.interface';
import { Session } from 'src/app/interfaces/auth/session.interface';
import { UpdateRequest } from 'src/app/interfaces/auth/updateRequest.interface';
import { Topic } from 'src/app/interfaces/topic.interface';
import { AuthService } from 'src/app/services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { ToastService } from 'src/app/services/toast.service';
import { TopicService } from 'src/app/services/topic.service';
import { UserService } from 'src/app/services/user.service';

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
    ReactiveFormsModule
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit {
  public hideNewPassword = true;
  public hidePassword = true;
  public $userData!: Observable<{ user: Session, subscriptions: Topic[] }>;

  public form = this.fb.group({
    username: [''],
    email: [''],
    newPassword: [''],
    password: ['', [Validators.required, Validators.minLength(8)]]

  });
  public isSubmitting = false;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private topicService: TopicService,
    private authService: AuthService,
    private toastService: ToastService,
    private router: Router
  ) { }

  ngOnInit() {
    this.$userData = combineLatest({
      user: this.sessionService.$user(),
      subscriptions: this.topicService.getSubscriptions()
    }).pipe(
      filter(({ user }) => user !== undefined),
      map(({ user, subscriptions }) => ({
        user: user as Session,
        subscriptions
      }))
    );

    this.$userData.subscribe(data => {
      if (data.user) {
        this.form.patchValue({
          username: data.user.username,
          email: data.user.email
        });
      }
    });
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
    ).subscribe({
      next: () => {
        console.log('Profile updated successfully');
      },
      error: (err) => {
        console.error('Error updating profile', err);
      }
    });
  }
  public unsubscribe(topicId: number): void {
    this.topicService.unsubscribe(topicId).pipe(
      tap(() => {
        // this.$userData = this.$userData.pipe(
        this.$userData.pipe(
          map(data => (console.log({
            ...data,
            subscriptions: data.subscriptions.filter(sub => sub.id !== topicId)
          })))
        );
      }),
      catchError((error) => {
        this.toastService.showError(error);
        return EMPTY;
      })
    ).subscribe();
  }

  public logOut(): void {
    this.sessionService.logOut();
    this.router.navigate(['']);
  }
}
