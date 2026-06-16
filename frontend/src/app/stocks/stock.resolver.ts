import { inject } from '@angular/core';
import { ResolveFn } from '@angular/router';
import { Stock } from '../shared/stock.model';
import { StockService } from './stock.service';

export const stockResolver: ResolveFn<Stock[]> = () => {
  const stockService = inject(StockService);
  // Get the current value from the signal or service
  return stockService.stocksChanged();
};
