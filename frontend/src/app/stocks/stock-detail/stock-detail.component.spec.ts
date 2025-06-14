import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA } from '@angular/core';

import { StockDetailComponent } from './stock-detail.component';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { LoadingSpinnerComponent } from '../../shared/loading-spinner/loading-spinner.component';

describe('StockDetailComponent', () => {
  let component: StockDetailComponent;
  let fixture: ComponentFixture<StockDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
    declarations: [StockDetailComponent, LoadingSpinnerComponent],
    imports: [RouterTestingModule,
        FormsModule],
    providers: [provideHttpClient(withInterceptorsFromDi()), provideHttpClientTesting()],
    schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StockDetailComponent);
    component = fixture.componentInstance;
    component.isLoading = true;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
