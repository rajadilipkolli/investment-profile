import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { InvestmentService } from '../shared/investment.service';
import { Stock } from '../shared/stock.model';

@Injectable({
  providedIn: 'root'
})
export class StockService {
  stocksChanged = new Subject<Stock[]>();

  private stocks: Stock[] = [];

  constructor(private investmentService: InvestmentService) { }

  loadStocks() {
    this.investmentService.getAvailableStocks()
      .subscribe(
        resData => {
          console.log('Available Stocks');
          console.log(resData);
          this.setStocks(resData);
        },
        errorMessage => {
          console.log(errorMessage);
        }
      );
  }

  setStocks(stocks: Stock[]) {
    this.stocks = stocks;
    this.stocksChanged.next(this.stocks.slice());
  }

  getStocks() {
    return this.stocks.slice();
  }

  getStock(index: number) {
    return this.stocks[index];
  }

  addStock(stock: Stock) {
    this.stocks.push(stock);
    this.stocksChanged.next(this.stocks.slice());
  }

  updateStock(index: number, newStock: Stock) {
    this.stocks[index] = newStock;
    this.stocksChanged.next(this.stocks.slice());
  }

  deleteStock(index: number) {
    this.stocks.splice(index, 1);
    this.stocksChanged.next(this.stocks.slice());
  }
}
