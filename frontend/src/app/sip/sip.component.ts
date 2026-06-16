import { Component, inject } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { InvestmentService } from '../shared/investment.service';
import { LoadingSpinnerComponent } from '../shared/loading-spinner/loading-spinner.component';

@Component({
    selector: 'app-sip',
    templateUrl: './sip.component.html',
    styleUrls: ['./sip.component.css'],
    imports: [LoadingSpinnerComponent, ReactiveFormsModule]
})
export class SipComponent {
  isLoading: boolean;
  error: string | null = null;
  defaultDuration = '1 year';
  durationList: string[] = ['1 year', '2 year', '3 year', '4 year', '5 year', '6 year', '7 year', '8 year', '9 year', '10 year'];

  amount: number;
  ror: number;
  durationString: string;
  duration: number;
  investment: number;
  predictedReturn: number;

  private investmentService = inject(InvestmentService);
  private router = inject(Router);

  sipForm = new FormGroup({
    amount: new FormControl<number | null>(null, [Validators.required, Validators.min(1)]),
    duration: new FormControl<string>('1 year', Validators.required),
    ror: new FormControl<number | null>(null, [Validators.required, Validators.min(1)])
  });

  onCalculate() {
    if (!this.sipForm.valid) {
      return;
    }
    this.amount = this.sipForm.value.amount!;
    this.ror = this.sipForm.value.ror!;
    this.durationString = this.sipForm.value.duration!;

    this.defaultDuration = this.sipForm.value.duration!;

    this.isLoading = true;

    this.duration = +this.durationString.replace(' year', '');
    this.investment = 12 * this.duration * this.amount;
    const predictedSipReturnObs: Observable<number> = this.investmentService.calculateSip(this.amount, this.ror, this.duration, this.investment);

    predictedSipReturnObs.subscribe({
      next: (resData) => {
        console.log(resData);
        this.isLoading = false;
        this.predictedReturn = resData;
      },
      error: (errorMessage) => {
        console.log(errorMessage);
        this.error = errorMessage;
        this.isLoading = false;
      }
    });
  }

  onClear() {
    this.investment = 0;
    this.predictedReturn = 0;
  }
}
