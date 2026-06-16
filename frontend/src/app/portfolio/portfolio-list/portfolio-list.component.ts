import { Component, OnInit, inject } from '@angular/core';
import { Router, ActivatedRoute, RouterLinkActive, RouterLink } from '@angular/router';
import { PortfolioService } from '../portfolio.service';

@Component({
    selector: 'app-portfolio-list',
    templateUrl: './portfolio-list.component.html',
    styleUrls: ['./portfolio-list.component.css'],
    imports: [RouterLinkActive, RouterLink]
})
export class PortfolioListComponent implements OnInit {
  private portfolioService = inject(PortfolioService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  investments = this.portfolioService.investmentsChanged;

  ngOnInit() {
    if (this.investments().length === 0) {
      this.portfolioService.loadUserInvestments();
    }
  }
}
