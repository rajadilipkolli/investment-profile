import { Component, OnInit, inject } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { InvestmentService } from 'src/app/shared/investment.service';
import { Stock } from 'src/app/shared/stock.model';
import { StockService } from '../stock.service';
import { LoadingSpinnerComponent } from '../../shared/loading-spinner/loading-spinner.component';

@Component({
    selector: 'app-stock-detail',
    templateUrl: './stock-detail.component.html',
    styleUrls: ['./stock-detail.component.css'],
    imports: [LoadingSpinnerComponent, ReactiveFormsModule]
})
export class StockDetailComponent implements OnInit {
  isLoading = false;
  error: string | null = null;
  stock: Stock | undefined;
  id = 0;
  defaultDuration = '1 year';
  durationList: string[] = ['1 year', '2 year', '3 year', '4 year', '5 year', '6 year', '7 year', '8 year', '9 year', '10 year'];

  durationString: string;
  duration: number;
  quantity: number;

  private stockService = inject(StockService);
  private investmentService = inject(InvestmentService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  stockForm = new FormGroup({
    name: new FormControl({ value: '', disabled: true }),
    investmentType: new FormControl({ value: '', disabled: true }),
    currentPrice: new FormControl({ value: 0, disabled: true }),
    anticipatedGrowth: new FormControl({ value: 0, disabled: true }),
    term: new FormControl({ value: 0, disabled: true }),
    quantity: new FormControl(0, [Validators.required, Validators.min(1)])
  });

  ngOnInit() {
    this.route.params
      .subscribe(
        (params: Params) => {
          this.id = +params['id'];
          this.stock = this.stockService.getStock(this.id);
          if (this.stock) {
             this.stockForm.patchValue({
               name: this.stock.name,
               investmentType: this.stock.investmentType,
               currentPrice: this.stock.currentPrice,
               anticipatedGrowth: this.stock.anticipatedGrowth,
               term: this.stock.term,
               quantity: 0
             });
          } else {
             this.router.navigate(['/not-found']);
          }
        }
      );
  }

  onBuy() {
    if (!this.stockForm.valid || !this.stock) {
      return;
    }

    this.quantity = this.stockForm.value.quantity!;
    const stockToBuy: Stock = { ...this.stock, quantity: this.quantity };

    this.isLoading = true;

    const addStockStatusObs: Observable<string> = this.investmentService.addStock(stockToBuy);

    addStockStatusObs.subscribe({
      next: resData => {
        console.log('here is response of save stock');
        console.log(resData);
        this.isLoading = false;
        this.stockForm.reset();
        this.router.navigate(['/portfolio']);
      },
      error: errorMessage => {
        console.log(errorMessage);
        this.error = errorMessage;
        this.isLoading = false;
      }
    });


  }
}
