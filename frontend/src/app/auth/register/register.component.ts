import { Component, inject } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService, SignInResponseData } from '../auth.service';
import { LoadingSpinnerComponent } from '../../shared/loading-spinner/loading-spinner.component';
import { MustMatch } from './must-match.validator';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css'],
    imports: [LoadingSpinnerComponent, ReactiveFormsModule]
})
export class RegisterComponent {
  isLoading = false;
  error: string | null = null;

  private authService = inject(AuthService);
  private router = inject(Router);

  registerForm = new FormGroup({
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    pan: new FormControl('', Validators.required),
    phone: new FormControl('', [Validators.required, Validators.pattern('^[0-9]+$')]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
    confirmPassword: new FormControl('', Validators.required)
  }, { validators: MustMatch('password', 'confirmPassword') });

  get f() { return this.registerForm.controls; }

  onSubmit() {
    if (!this.registerForm.valid) {
      return;
    }
    const { email, password, firstName, lastName, pan, phone } = this.registerForm.value;

    this.isLoading = true;
    const authObs: Observable<SignInResponseData> = this.authService.signup(email!, password!, firstName!, lastName!, pan!, +phone!);

    authObs.subscribe({
      next: (resData) => {
        console.log(resData);
        this.isLoading = false;
        this.router.navigate(['/portfolio']);
      },
      error: (errorMessage) => {
        console.log(errorMessage);
        this.error = errorMessage;
        this.isLoading = false;
      }
    });

    this.registerForm.reset();
  }
}
