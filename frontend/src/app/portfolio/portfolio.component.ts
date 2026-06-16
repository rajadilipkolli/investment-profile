import { Component, OnInit, inject } from '@angular/core';
import { InvestmentService } from '../shared/investment.service';
import { RouterOutlet } from '@angular/router';
import { PortfolioListComponent } from './portfolio-list/portfolio-list.component';

@Component({
    selector: 'app-portfolio',
    templateUrl: './portfolio.component.html',
    styleUrls: ['./portfolio.component.css'],
    imports: [RouterOutlet, PortfolioListComponent]
})
export class PortfolioComponent implements OnInit {
  isLoading = false;
  error: string | null = null;

  private investmentService = inject(InvestmentService);
}
