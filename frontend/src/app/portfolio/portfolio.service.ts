import { Injectable, signal, inject } from '@angular/core';
import { InvestmentService } from '../shared/investment.service';
import { Portfolio } from '../shared/portfolio.model';

@Injectable({
  providedIn: 'root'
})
export class PortfolioService {
  investmentsChanged = signal<Portfolio[]>([]);

  private investmentService = inject(InvestmentService);

  setinvestments(investments: Portfolio[]) {
    this.investmentsChanged.set(investments);
  }

  getInvestments() {
    return this.investmentsChanged();
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
    return this.investmentsChanged()[index];
  }

  addPortfolio(portfolio: Portfolio) {
    this.investmentsChanged.update(investments => [...investments, portfolio]);
  }

  updatePortfolio(index: number, newPortfolio: Portfolio) {
    this.investmentsChanged.update(investments => {
      const updated = [...investments];
      updated[index] = newPortfolio;
      return updated;
    });
  }

  deletePortfolio(index: number) {
    this.investmentsChanged.update(investments => {
      const updated = [...investments];
      updated.splice(index, 1);
      return updated;
    });
  }
}
