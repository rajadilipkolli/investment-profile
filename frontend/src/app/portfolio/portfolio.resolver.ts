import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { Portfolio } from '../shared/portfolio.model';
import { PortfolioService } from './portfolio.service';
import { toObservable } from '@angular/core/rxjs-interop';
import { skip, take } from 'rxjs/operators';

export const portfolioResolver: ResolveFn<Portfolio[]> = () => {
  const portfolioService = inject(PortfolioService);
  portfolioService.loadUserInvestments();
  return toObservable(portfolioService.investmentsChanged).pipe(
    skip(1),
    take(1)
  );
};
