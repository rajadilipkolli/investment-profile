import { Component, ElementRef, OnInit, Renderer2, ViewChild, ViewChildren } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { InvestmentService } from 'src/app/shared/investment.service';
import { Portfolio } from 'src/app/shared/portfolio.model';
import { PortfolioService } from '../portfolio.service';

@Component({
    selector: 'app-portfolio-detail',
    templateUrl: './portfolio-detail.component.html',
    styleUrls: ['./portfolio-detail.component.css'],
    standalone: false
})
export class PortfolioDetailComponent implements OnInit {

  isLoading: boolean = false;
  deleteClicked: boolean = false;
  portfolio: Portfolio;
  id: number;
  error: string = null;
  subscription: Subscription;

  constructor(private portfolioService: PortfolioService,
    private investmentService: InvestmentService,
    private route: ActivatedRoute,
    private router: Router,
    private renderer: Renderer2) {
  }

  ngOnInit() {
    this.route.params
      .subscribe(
        (params: Params) => {
          this.id = +params['id'];
          this.portfolio = this.portfolioService.getPortfolio(this.id);
          this.subscription = this.portfolioService.investmentsChanged.subscribe(
            resData => {
              this.portfolio = resData[this.id];
              if (this.portfolio === undefined) {
                this.router.navigate(['/not-found']);
              }
            }
          );
        }
      );
  }

  onPorfolioSubmit(form: NgForm) {
    if (!form.valid) {
      return;
    }
    this.isLoading = true;
    if (this.deleteClicked) {
      this.investmentService.deletePortfolio(this.portfolio).subscribe(
        resData => {
          this.isLoading = false;
          console.log('delete response');
          console.log(resData);
          if ('SUCCESS' === resData.status) {
            this.portfolioService.deletePortfolio(this.id, this.portfolio);
          }
        },
        errorMessage => {
          this.isLoading = false;
          console.log(errorMessage);
        }
      );
      this.router.navigate(['/portfolio']);
    } else {
      this.portfolio.quantity = form.value.quantity;
      this.investmentService.updatePortfolio(this.portfolio).subscribe(
        resData => {
          this.isLoading = false;
          console.log('update response');
          console.log(resData);
          if (resData !== undefined && resData.name !== '') {
            this.portfolioService.updatePortfolio(this.id, this.portfolio);
          }
        },
        errorMessage => {
          this.isLoading = false;
          console.log(errorMessage);
        }
      );
    }
  }


  onDeleteClick() {
    this.deleteClicked = true;
  }

  onUpdateClick() {
    this.deleteClicked = false;
  }
  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
