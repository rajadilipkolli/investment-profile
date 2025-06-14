import { Component } from '@angular/core';
import { AuthService, AuthResponseData, SignInResponseData } from '../auth.service';
import { NgForm } from '@angular/forms';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { PortfolioService } from 'src/app/portfolio/portfolio.service';
import { StockService } from 'src/app/stocks/stock.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css'],
    standalone: false
})
export class LoginComponent {
  isLoading = false;
  error: string = null;

  constructor(private authService: AuthService, private router: Router,
    private portfolioService: PortfolioService, private stockService: StockService) { }

  onSubmit(form: NgForm) {
    if (!form.valid) {
      return;
    }
    const email = form.value.email;
    const password = form.value.password;

    let authObs: Observable<SignInResponseData>;

    this.isLoading = true;
    authObs = this.authService.login(email, password);

    authObs.subscribe(
      resData => {
        console.log(resData);
        this.isLoading = false;
        this.portfolioService.loadUserInvestments();
        this.stockService.loadStocks();
        this.router.navigate(['/portfolio']);
      },
      errorMessage => {
        console.log(errorMessage);
        this.error = errorMessage;
        this.isLoading = false;
      }
    );
    form.reset();
  }
}
