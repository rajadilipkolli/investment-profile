import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { Portfolio } from '../shared/portfolio.model';
import { PortfolioService } from './portfolio.service';

export const portfolioResolver: ResolveFn<Portfolio[]> = () => {
  const portfolioService = inject(PortfolioService);
  // Get the current value from the signal or service
  return portfolioService.investmentsChanged();
};
