import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthComponent } from './auth/auth.component';
import { PortfolioComponent } from './portfolio/portfolio.component';
import { StocksComponent } from './stocks/stocks.component';
import { SipComponent } from './sip/sip.component';
import { AuthGuard } from './auth/auth.guard';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { PortfolioDetailComponent } from './portfolio/portfolio-detail/portfolio-detail.component';
import { PortfolioResolverService } from './portfolio/portfolio-resolver.service';
import { StockDetailComponent } from './stocks/stock-detail/stock-detail.component';
import { StockResolverService } from './stocks/stock-resolver.service';

const routes: Routes = [
  { path: '', redirectTo: '/portfolio', pathMatch: 'full' },
  { path: 'auth', component: AuthComponent },
  {
    path: 'portfolio', component: PortfolioComponent, resolve: { portfolioList: PortfolioResolverService }, canActivate: [AuthGuard],
    children: [
      {
        path: ':id',
        component: PortfolioDetailComponent
      }
    ]
  },
  { path: 'sip-calculator', component: SipComponent, canActivate: [AuthGuard] },
  {
    path: 'stocks', component: StocksComponent, canActivate: [AuthGuard], resolve: { stockList: StockResolverService },
    children: [
      {
        path: ':id',
        component: StockDetailComponent
      }
    ]
  },
  { path: 'not-found', component: PageNotFoundComponent },
  { path: '**', redirectTo: 'not-found' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
