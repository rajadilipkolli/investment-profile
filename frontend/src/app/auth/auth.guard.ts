import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from './auth.service';

export const authGuard: CanActivateFn = () => {
    const authService = inject(AuthService);
    const router = inject(Router);

    const user = authService.user();
    const isAuth = !!user;
    if (isAuth) {
        return true;
    }
    return router.createUrlTree(['/auth']);
};
