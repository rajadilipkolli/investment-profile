import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Portfolio } from './portfolio.model';
import { Stock } from './stock.model';

@Injectable({
  providedIn: 'root'
})


export class InvestmentService {

  addStock(stockToBuy: Stock) {
    const authToken = localStorage.getItem('authToken');
    return this.http.post<string>(
      'http://localhost:8085/api-gateway/restservices/buy/stock',
      stockToBuy,
      {
        headers: new HttpHeaders({ Authorization: authToken })
      }
    ).pipe(
      catchError(this.handleError)
    );

  }

  predictedSipRetun: number = 2000;

  constructor(private http: HttpClient, private router: Router) { }

  calculateSip(amount: any, ror: any, duration: number, investment: any) {
    const authToken = localStorage.getItem('authToken');
    return this.http.post<number>(
      'http://localhost:8085/api-gateway/sip/calculator/sip',
      {
        monthlyInvestment: amount,
        expectedRateOfInterest: ror,
        investmentDuration: duration,
        investedAmount: investment
      },
      {
        headers: new HttpHeaders({ 'Authorization': authToken })
      }
    ).pipe(
      catchError(this.handleError)
    );
  }

  getAvailableStocks() {
    const authToken = localStorage.getItem('authToken');
    return this.http.get<Stock[]>(
      'http://localhost:8085/api-gateway/stock/view/all',
      {
        headers: new HttpHeaders({ 'Authorization': authToken })
      }
    ).pipe(
      catchError(this.handleError)
    );
  }

  getAllInvestments() {
    const authToken = localStorage.getItem('authToken');
    return this.http.get<Portfolio[]>(
      'http://localhost:8085/api-gateway/restservices/investments/all',
      {
        headers: new HttpHeaders({ 'Authorization': authToken })
      }
    ).pipe(
      // delay(10000),
      catchError(this.handleError)
    );
  }

  updatePortfolio(newPortfolio: Portfolio) {
    const authToken = localStorage.getItem('authToken');
    return this.http.post<Portfolio>(
      'http://localhost:8085/api-gateway/restservices/investments/update',
      newPortfolio,
      {
        headers: new HttpHeaders({ 'Authorization': authToken })
      }
    ).pipe(
      catchError(this.handleError)
    );
  }

  deletePortfolio(portfolioToDelete: Portfolio) {
    const authToken = localStorage.getItem('authToken');
    return this.http.post<{ status: string }>(
      'http://localhost:8085/api-gateway/restservices/investments/delete',
      portfolioToDelete,
      {
        headers: new HttpHeaders({ 'Authorization': authToken })
      }
    ).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(errorRes: HttpErrorResponse) {
    let errorMessage = 'An unknown error occured!';
    if (!errorRes.error || !errorRes.error.error) {
      return throwError(errorMessage);
    }

    switch (errorRes.error.error.message) {
      case 'EMAIL_EXISTS':
        errorMessage = 'This email exists already';
        break;
      case 'EMAIL_NOT_FOUND':
        errorMessage = 'This email does not exist.';
        break;
      case 'INVALID_PASSWORD':
        errorMessage = 'The password is not correct.';
        break;
    }
    return throwError(errorMessage);
  }

}
