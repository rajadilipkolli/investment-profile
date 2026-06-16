import { Component, inject } from '@angular/core';
import { AuthService, SignInResponseData } from '../auth.service';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { PortfolioService } from 'src/app/portfolio/portfolio.service';
import { StockService } from 'src/app/stocks/stock.service';
import { LoadingSpinnerComponent } from '../../shared/loading-spinner/loading-spinner.component';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css'],
    imports: [LoadingSpinnerComponent, ReactiveFormsModule]
})
export class LoginComponent {
  isLoading = false;
  error: string | null = null;

  private authService = inject(AuthService);
  private router = inject(Router);
  private portfolioService = inject(PortfolioService);
  private stockService = inject(StockService);

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required])
  });

  onSubmit() {
    if (!this.loginForm.valid) {
      return;
    }
    const email = this.loginForm.value.email!;
    const password = this.loginForm.value.password!;

    this.isLoading = true;
    const authObs: Observable<SignInResponseData> = this.authService.login(email, password);

    authObs.subscribe({
      next: (resData) => {
        console.log(resData);
        this.isLoading = false;
        this.portfolioService.loadUserInvestments();
        this.stockService.loadStocks();
        this.router.navigate(['/portfolio']);
      },
      error: (errorMessage) => {
        console.log(errorMessage);
        this.error = errorMessage;
        this.isLoading = false;
      }
    });
    this.loginForm.reset();
  }
}
