import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule } from '@angular/forms';
import { SessionService } from 'src/app/services/session.service';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { RegisterRequest } from 'src/app/interfaces/auth/registerRequest.interface';
import { catchError, switchMap, tap, throwError } from 'rxjs';
import { Jwt } from 'src/app/interfaces/auth/jwt.interface';
import { Session } from 'src/app/interfaces/auth/session.interface';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    ReactiveFormsModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})

export class RegisterComponent {
  public form: FormGroup = this.fb.group({
    username: ['', [Validators.required, Validators.minLength(3)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8), this.passwordValidator()]]
  });;
  public onError = false;
  public hide = true;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private sessionService: SessionService
  ) { }

  public submit(): void {
    if (this.form.invalid) {
      return;
    }

    const registerRequest = this.form.getRawValue() as RegisterRequest;

    this.authService.register(registerRequest).pipe(
      tap((response: Jwt) => localStorage.setItem('authToken', response.token)),
      switchMap(() => this.authService.me()),
      tap((user: Session) => {
        this.sessionService.logIn(user);
        this.router.navigate(['/']);
      }),
      catchError((error) => {
        this.onError = true;
        return throwError(() => error);
      })
    ).subscribe();
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
