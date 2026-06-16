import { Component, OnInit, inject } from '@angular/core';
import { Router, ActivatedRoute, RouterLinkActive, RouterLink } from '@angular/router';
import { StockService } from '../stock.service';

@Component({
    selector: 'app-stock-list',
    templateUrl: './stock-list.component.html',
    styleUrls: ['./stock-list.component.css'],
    imports: [RouterLinkActive, RouterLink]
})
export class StockListComponent implements OnInit {
  private stockService = inject(StockService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  stocks = this.stockService.stocksChanged;

  ngOnInit() {
    if (this.stocks().length === 0) {
      this.stockService.loadStocks();
    }
  }
}
