import { Routes } from '@angular/router';
import { authGuard } from './auth/auth.guard';
import { portfolioResolver } from './portfolio/portfolio.resolver';
import { stockResolver } from './stocks/stock.resolver';

export const routes: Routes = [
  { path: '', redirectTo: '/portfolio', pathMatch: 'full' },
  { path: 'auth', loadComponent: () => import('./auth/auth.component').then(m => m.AuthComponent) },
  {
    path: 'portfolio', 
    loadComponent: () => import('./portfolio/portfolio.component').then(m => m.PortfolioComponent),
    resolve: { portfolioList: portfolioResolver }, 
    canActivate: [authGuard],
    children: [
      {
        path: ':id',
        loadComponent: () => import('./portfolio/portfolio-detail/portfolio-detail.component').then(m => m.PortfolioDetailComponent)
      }
    ]
  },
  { path: 'sip-calculator', loadComponent: () => import('./sip/sip.component').then(m => m.SipComponent), canActivate: [authGuard] },
  {
    path: 'stocks', 
    loadComponent: () => import('./stocks/stocks.component').then(m => m.StocksComponent), 
    canActivate: [authGuard], 
    resolve: { stockList: stockResolver },
    children: [
      {
        path: ':id',
        loadComponent: () => import('./stocks/stock-detail/stock-detail.component').then(m => m.StockDetailComponent)
      }
    ]
  },
  { path: 'not-found', loadComponent: () => import('./page-not-found/page-not-found.component').then(m => m.PageNotFoundComponent) },
  { path: '**', redirectTo: 'not-found' }
];
