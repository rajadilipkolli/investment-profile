import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { InvestmentService } from '../shared/investment.service';
import { Portfolio } from '../shared/portfolio.model';

@Injectable({
  providedIn: 'root'
})
export class PortfolioService {
  investmentsChanged = new Subject<Portfolio[]>();

  private investments: Portfolio[] = [];

  constructor(private investmentService: InvestmentService) { }

  setinvestments(investments: Portfolio[]) {
    this.investments = investments;
    this.investmentsChanged.next(this.investments.slice());
  }

  getInvestments() {
    return this.investments.slice();
  }

  loadUserInvestments() {
    this.investmentService.getAllInvestments()
      .subscribe(
        resData => {
          console.log('All Investments');
          console.log(resData);
          this.setinvestments(resData);
        },
        errorMessage => {
          console.log(errorMessage);
        }
      );
  }

  getPortfolio(index: number) {
    return this.investments[index];
  }

  addPortfolio(portfolio: Portfolio) {
    this.investments.push(portfolio);
    this.investmentsChanged.next(this.investments.slice());
  }

  updatePortfolio(index: number, newPortfolio: Portfolio) {
    this.investments[index] = newPortfolio;
    this.investmentsChanged.next(this.investments.slice());
  }

  deletePortfolio(index: number, portfolioToDelete: Portfolio) {
    this.investments.splice(index, 1);
    this.investmentsChanged.next(this.investments.slice());
  }
}
