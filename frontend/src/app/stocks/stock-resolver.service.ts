import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { InvestmentService } from '../shared/investment.service';
import { Stock } from '../shared/stock.model';
import { StockService } from './stock.service';

@Injectable({
  providedIn: 'root'
})
export class StockResolverService  {

  constructor(private stockService: StockService) { }

  private stocks: Stock[] = [];

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    this.stockService.stocksChanged.subscribe(
      stockList => {
        this.stocks = stockList;
      }
    );
    return this.stocks;
  }
}
