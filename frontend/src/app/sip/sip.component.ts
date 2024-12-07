import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { InvestmentService } from '../shared/investment.service';

@Component({
    selector: 'app-sip',
    templateUrl: './sip.component.html',
    styleUrls: ['./sip.component.css'],
    standalone: false
})
export class SipComponent implements OnInit {
  isLoading: boolean;
  error: string = null;
  defaultDuration = '1 year';
  durationList: string[] = ['1 year', '2 year', '3 year', '4 year', '5 year', '6 year', '7 year', '8 year', '9 year', '10 year'];

  amount: number;
  ror: number;
  durationString: string;
  duration: number;
  investment: number;
  predictedReturn: number;
  constructor(private investmentService: InvestmentService, private router: Router) { }

  onCalculate(form: NgForm) {
    if (!form.valid) {
      return;
    }
    this.amount = form.value.amount;
    this.ror = form.value.ror;
    this.durationString = form.value.duration;

    this.defaultDuration = form.value.duration;


    let predictedSipReturnObs: Observable<number>;

    this.isLoading = true;

    this.duration = +this.durationString.replace(' year', '');
    this.investment = 12 * this.duration * form.value.amount;
    predictedSipReturnObs = this.investmentService.calculateSip(this.amount, this.ror, this.duration, this.investment);

    predictedSipReturnObs.subscribe(
      resData => {
        console.log(resData);
        this.isLoading = false;
        this.predictedReturn = resData;
        // this.router.navigate(['/portfolio']);
      },
      errorMessage => {
        console.log(errorMessage);
        this.error = errorMessage;
        this.isLoading = false;
      }
    );

    // form.reset();
  }

  ngOnInit(): void { }

}
