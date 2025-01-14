import { ChangeDetectionStrategy, Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { AuthService } from 'src/app/services/auth.service';
import { Session } from 'src/app/interfaces/auth/session.interface';
import { Jwt } from 'src/app/interfaces/auth/jwt.interface';
import { LoginRequest } from 'src/app/interfaces/auth/loginRequest.interface';
import { switchMap, tap } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})

export class LoginComponent {
  public hide = true;
  public onError = false;

  public form: FormGroup = this.fb.group({
    identifier: ['', [Validators.required, Validators.minLength(3)]],
    password: ['', [Validators.required, Validators.minLength(8)]]
  });;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private sessionService: SessionService,
  ) { }

  public submit(): void {
    if (this.form.invalid) {
      return;
    }

    const loginRequest = this.form.getRawValue() as LoginRequest;

    this.authService.login(loginRequest).pipe(
      tap((response: Jwt) => localStorage.setItem('authToken', response.token)),
      switchMap(() => this.authService.me()),
      tap((user: Session) => {
        this.sessionService.logIn(user);
        this.router.navigate(['/']);
      })
    ).subscribe(
    );
  }
}
