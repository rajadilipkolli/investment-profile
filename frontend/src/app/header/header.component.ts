import { Component, computed, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    imports: [RouterLink, RouterLinkActive]
})
export class HeaderComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  isAuthenticated = computed(() => !!this.authService.user());

  onLogout() {
    this.authService.logout();
    this.router.navigate(['/auth']);
  }
}
