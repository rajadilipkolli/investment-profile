import { Component, OnInit, inject } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { HeaderComponent } from './header/header.component';
import { RouterOutlet } from '@angular/router';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css'],
    imports: [HeaderComponent, RouterOutlet]
})
export class AppComponent implements OnInit {

  title = 'investment-profile';

  private authService = inject(AuthService);

  ngOnInit(): void {
    this.authService.autoLogin();
  }

}
