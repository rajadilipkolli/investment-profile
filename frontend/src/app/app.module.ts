import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PortfolioComponent } from './portfolio/portfolio.component';
import { LoadingSpinnerComponent } from './shared/loading-spinner/loading-spinner.component';
import { SipComponent } from './sip/sip.component';
import { StocksComponent } from './stocks/stocks.component';
import { AuthComponent } from './auth/auth.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DropdownDirective } from './shared/dropdown.directive';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './auth/login/login.component';
import { MustMatchDirective } from './auth/register/must-match.directive';
import { HttpClientModule } from '@angular/common/http';
import { RegisterComponent } from './auth/register/register.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { MatTableModule } from '@angular/material/table';
import { PortfolioDetailComponent } from './portfolio/portfolio-detail/portfolio-detail.component';
import { PortfolioListComponent } from './portfolio/portfolio-list/portfolio-list.component';
import { StockListComponent } from './stocks/stock-list/stock-list.component';
import { StockDetailComponent } from './stocks/stock-detail/stock-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    DropdownDirective,
    LoadingSpinnerComponent,
    HeaderComponent,
    RegisterComponent,
    LoginComponent,
    MustMatchDirective,
    PortfolioComponent,
    SipComponent,
    StocksComponent,
    PageNotFoundComponent,
    PortfolioDetailComponent,
    PortfolioListComponent,
    StockListComponent,
    StockDetailComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    HttpClientModule,
    MatTableModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
