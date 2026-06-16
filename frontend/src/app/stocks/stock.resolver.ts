import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { Stock } from '../shared/stock.model';
import { StockService } from './stock.service';
import { toObservable } from '@angular/core/rxjs-interop';
import { skip, take } from 'rxjs/operators';

export const stockResolver: ResolveFn<Stock[]> = () => {
  const stockService = inject(StockService);
  stockService.loadStocks();
  return toObservable(stockService.stocksChanged).pipe(
    skip(1),
    take(1)
  );
};
