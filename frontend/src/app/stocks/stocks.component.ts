import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { StockListComponent } from './stock-list/stock-list.component';

export interface PeriodicElement {
  name: string;
  type: string;
  price: number;
  growth: number;
  term: number;
}

@Component({
    selector: 'app-stocks',
    templateUrl: './stocks.component.html',
    styleUrls: ['./stocks.component.css'],
    imports: [RouterOutlet, StockListComponent]
})
export class StocksComponent {
}
