import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, Data } from '@angular/router';
import { Subscription } from 'rxjs';
import { Portfolio } from 'src/app/shared/portfolio.model';
import { PortfolioService } from '../portfolio.service';

@Component({
    selector: 'app-portfolio-list',
    templateUrl: './portfolio-list.component.html',
    styleUrls: ['./portfolio-list.component.css'],
    standalone: false
})
export class PortfolioListComponent implements OnInit, OnDestroy {
  investments: Portfolio[];
  subscription: Subscription;

  constructor(private portfolioService: PortfolioService, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.subscription = this.callInvestmentChangedSubject();

    // this.route.data.subscribe(
    //   (data: Data)=> {
    //     this.investments = data['portfolioList'];
    //   }
    // );

    if (this.investments === undefined || this.investments.length === 0) {
      this.portfolioService.loadUserInvestments();
      this.subscription = this.callInvestmentChangedSubject();
    }
  }

  callInvestmentChangedSubject(): Subscription {
    return this.portfolioService.investmentsChanged
      .subscribe(
        (investments: Portfolio[]) => {
          this.investments = investments;
        }
      );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
