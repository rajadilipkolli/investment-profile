import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { InvestmentService } from '../shared/investment.service';
import { Portfolio } from '../shared/portfolio.model';
import { PortfolioService } from './portfolio.service';

@Injectable({
  providedIn: 'root'
})
export class PortfolioResolverService  {
  investmentList: Portfolio[];
  constructor(
    private investmentService: InvestmentService,
    private portfolioService: PortfolioService
  ) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    this.portfolioService.investmentsChanged.subscribe(
      investmentList => {
        this.investmentList = investmentList;
      }
    );
    return this.investmentList;
  }
}
