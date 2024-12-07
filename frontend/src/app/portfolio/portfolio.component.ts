import { Component, OnInit } from '@angular/core';
import { InvestmentService } from '../shared/investment.service';

@Component({
    selector: 'app-portfolio',
    templateUrl: './portfolio.component.html',
    styleUrls: ['./portfolio.component.css'],
    standalone: false
})
export class PortfolioComponent implements OnInit {
  isLoading: boolean = false;
  error: string = null;

  constructor(private investmentService: InvestmentService) { }

  ngOnInit(): void {
  }

}
