import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Stock } from 'src/app/shared/stock.model';
import { StockService } from '../stock.service';

@Component({
  selector: 'app-stock-list',
  templateUrl: './stock-list.component.html',
  styleUrls: ['./stock-list.component.css']
})
export class StockListComponent implements OnInit, OnDestroy {
  stocks: Stock[];
  subscription: Subscription;

  constructor(private stockService: StockService,
    private router: Router,
    private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.subscription = this.callStocksChangedSubject();
    if (this.stocks === undefined || this.stocks.length === 0) {
      this.stockService.loadStocks();
      this.subscription = this.callStocksChangedSubject();
    }
  }
  callStocksChangedSubject(): Subscription {
    return this.stockService.stocksChanged
      .subscribe(
        (stocks: Stock[]) => {
          this.stocks = stocks;
        }
      );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
