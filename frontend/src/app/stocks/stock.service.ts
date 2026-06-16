import { Injectable, signal, inject } from '@angular/core';
import { InvestmentService } from '../shared/investment.service';
import { Stock } from '../shared/stock.model';

@Injectable({
  providedIn: 'root'
})
export class StockService {
  stocksChanged = signal<Stock[]>([]);

  private investmentService = inject(InvestmentService);

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
    this.stocksChanged.set([...stocks]);
  }

  getStocks() {
    return [...this.stocksChanged()];
  }

  getStock(index: number) {
    return this.stocksChanged()[index];
  }

  addStock(stock: Stock) {
    this.stocksChanged.update(stocks => [...stocks, stock]);
  }

  updateStock(index: number, newStock: Stock) {
    this.stocksChanged.update(stocks => {
      const updated = [...stocks];
      updated[index] = newStock;
      return updated;
    });
  }

  deleteStock(index: number) {
    this.stocksChanged.update(stocks => {
      const updated = [...stocks];
      updated.splice(index, 1);
      return updated;
    });
  }
}
