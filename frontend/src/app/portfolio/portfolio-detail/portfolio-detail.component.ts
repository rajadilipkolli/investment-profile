import { Component, OnInit, inject } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { InvestmentService } from 'src/app/shared/investment.service';
import { Portfolio } from 'src/app/shared/portfolio.model';
import { PortfolioService } from '../portfolio.service';
import { LoadingSpinnerComponent } from '../../shared/loading-spinner/loading-spinner.component';

@Component({
    selector: 'app-portfolio-detail',
    templateUrl: './portfolio-detail.component.html',
    styleUrls: ['./portfolio-detail.component.css'],
    imports: [LoadingSpinnerComponent, ReactiveFormsModule]
})
export class PortfolioDetailComponent implements OnInit {

  isLoading = false;
  deleteClicked = false;
  portfolio: Portfolio | null = null;
  id = 0;
  error: string | null = null;

  private portfolioService = inject(PortfolioService);
  private investmentService = inject(InvestmentService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  portfolioForm = new FormGroup({
    name: new FormControl({ value: '', disabled: true }),
    type: new FormControl({ value: '', disabled: true }),
    costPrice: new FormControl({ value: 0, disabled: true }),
    currentPrice: new FormControl({ value: 0, disabled: true }),
    quantity: new FormControl(0, [Validators.required, Validators.min(1)])
  });

  ngOnInit() {
    this.route.params
      .subscribe(
        (params: Params) => {
          this.id = +params['id'];
          this.portfolio = this.portfolioService.getPortfolio(this.id);
          if (this.portfolio) {
             this.portfolioForm.patchValue({
               name: this.portfolio.name,
               type: this.portfolio.type,
               costPrice: this.portfolio.costPrice,
               currentPrice: this.portfolio.currentPrice,
               quantity: this.portfolio.quantity
             });
          } else {
             this.router.navigate(['/not-found']);
          }
        }
      );
  }

  onPorfolioSubmit() {
    if (!this.portfolioForm.valid) {
      return;
    }
    this.isLoading = true;
    if (this.deleteClicked) {
      if (this.portfolio) {
        this.investmentService.deletePortfolio(this.portfolio).subscribe({
          next: resData => {
            this.isLoading = false;
            console.log('delete response');
            console.log(resData);
            if ('SUCCESS' === resData.status && this.portfolio) {
              this.portfolioService.deletePortfolio(this.id);
            }
          },
          error: errorMessage => {
            this.isLoading = false;
            console.log(errorMessage);
          }
        });
      }
      this.router.navigate(['/portfolio']);
    } else {
      if (this.portfolio) {
        this.portfolio.quantity = this.portfolioForm.value.quantity!;
        this.investmentService.updatePortfolio(this.portfolio).subscribe({
          next: resData => {
            this.isLoading = false;
            console.log('update response');
            console.log(resData);
            if (resData !== undefined && resData.name !== '' && this.portfolio) {
              this.portfolioService.updatePortfolio(this.id, this.portfolio);
            }
          },
          error: errorMessage => {
            this.isLoading = false;
            console.log(errorMessage);
          }
        });
      }
    }
  }


  onDeleteClick() {
    this.deleteClicked = true;
  }

  onUpdateClick() {
    this.deleteClicked = false;
  }
}
