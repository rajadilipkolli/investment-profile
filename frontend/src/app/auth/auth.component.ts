import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';

@Component({
    selector: 'app-auth',
    templateUrl: './auth.component.html',
    imports: [RegisterComponent, LoginComponent]
})
export class AuthComponent {
  error: string | null = null;

  private authService = inject(AuthService);
  private router = inject(Router);
}
