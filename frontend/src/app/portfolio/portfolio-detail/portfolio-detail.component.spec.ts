import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { PortfolioDetailComponent } from './portfolio-detail.component';
import { FormsModule } from '@angular/forms';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';
import { LoadingSpinnerComponent } from '../../shared/loading-spinner/loading-spinner.component';

describe('PortfolioDetailComponent', () => {
  let component: PortfolioDetailComponent;
  let fixture: ComponentFixture<PortfolioDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
    declarations: [PortfolioDetailComponent, LoadingSpinnerComponent],
    imports: [RouterTestingModule, FormsModule],
    providers: [provideHttpClient(withInterceptorsFromDi()), provideHttpClientTesting()],
    schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PortfolioDetailComponent);
    component = fixture.componentInstance;
    component.isLoading = true;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
