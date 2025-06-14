import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { InvestmentService } from './investment.service';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';

describe('InvestmentService', () => {
  let service: InvestmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
    imports: [RouterTestingModule],
    providers: [provideHttpClient(withInterceptorsFromDi()), provideHttpClientTesting()]
});
    service = TestBed.inject(InvestmentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
