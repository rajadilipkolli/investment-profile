import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { PortfolioService } from 'src/app/portfolio/portfolio.service';
import { InvestmentService } from 'src/app/shared/investment.service';
import { Stock } from 'src/app/shared/stock.model';
import { StockService } from '../stock.service';

@Component({
  selector: 'app-stock-detail',
  templateUrl: './stock-detail.component.html',
  styleUrls: ['./stock-detail.component.css']
})
export class StockDetailComponent implements OnInit {
  isLoading: boolean = false;
  error: string = null;
  stock: Stock = { name: '', investmentType: '', currentPrice: 0, anticipatedGrowth: 0, term: 0, quantity: 0 };
  id: number;
  defaultDuration = '1 year';
  durationList: string[] = ['1 year', '2 year', '3 year', '4 year', '5 year', '6 year', '7 year', '8 year', '9 year', '10 year'];

  durationString: string;
  duration: number;
  quantity: number;
  subscription: Subscription;

  constructor(private stockService: StockService,
    private investmentService: InvestmentService,
    private route: ActivatedRoute,
    private router: Router) {
  }

  ngOnInit() {
    this.route.params
      .subscribe(
        (params: Params) => {
          this.id = +params['id'];
          this.stock = this.stockService.getStock(this.id);
          this.subscription = this.stockService.stocksChanged.subscribe(
            resData => {
              this.stock = resData[this.id];
              if (this.stock === undefined) {
                this.router.navigate(['/not-found']);
              }
            }
          );
        }
      );
  }

  onBuy(form: NgForm) {
    if (!form.valid) {
      return;
    }

    this.quantity = form.value.quantity;
    let stockToBuy: Stock;

    stockToBuy = this.stock;
    stockToBuy.quantity = form.value.quantity;

    let addStockStatusObs: Observable<string>;

    this.isLoading = true;

    addStockStatusObs = this.investmentService.addStock(stockToBuy);

    addStockStatusObs.subscribe(
      resData => {
        console.log('here is response of save stock');
        console.log(resData);
        this.isLoading = false;
        // this.predictedReturn = resData;
        this.router.navigate(['/portfolio']);
      },
      errorMessage => {
        console.log(errorMessage);
        this.error = errorMessage;
        this.isLoading = false;
      }
    );

    form.reset();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
