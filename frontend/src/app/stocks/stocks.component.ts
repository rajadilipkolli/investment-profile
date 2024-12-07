import { Component, OnInit } from '@angular/core';

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
    standalone: false
})
export class StocksComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }

}
