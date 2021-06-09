import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { User } from './user.model';
import { Router } from '@angular/router';

export interface AuthResponseData {
  kind: string;
  idToken: string;
  email: string;
  refreshToken: string;
  expiresIn: string;
  localId: string;
  registered?: boolean;
}

export interface SignInResponseData {
  id: string;
  username: string;
  email: string;
  accessToken: string;
  tokenType: string;
  expiresIn: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  user = new BehaviorSubject<User>(null);
  token: string = null;
  private tokenExpirationTimer: any;

  constructor(private http: HttpClient, private router: Router) { }

  signup(email: string, password: string, firstName: string, lastName: string, pan: string, phone: number) {
    return this.http.post<SignInResponseData>(
      'http://localhost:8085/api-gateway/auth/signup',
      {
        email: email,
        password: password,
        firstName: firstName,
        lastName: lastName,
        pan: pan,
        phone: +phone,
        returnSecureToken: true
      }
    ).pipe(
      catchError(this.handleError),
      tap(resData => {
        this.handleSignInAuthentication(
          resData.username,
          resData.email,
          resData.tokenType,
          resData.accessToken,
          +resData.expiresIn
        );
      })
    );
  }

  public login(email: string, password: string) {
    return this.http.post<SignInResponseData>(
      'http://localhost:8085/api-gateway/auth/signin',
      {
        email: email,
        password: password
      }
    ).pipe(
      catchError(this.handleError),
      tap(resData => {
        this.handleSignInAuthentication(
          resData.username,
          resData.email,
          resData.tokenType,
          resData.accessToken,
          +resData.expiresIn
        );
      })
    );

  }

  private handleAuthentication(email: string, userId: string, token: string, expiresIn: number) {
    const expirationDate = new Date(new Date().getTime() + expiresIn * 1000);
    const user = new User(email, userId, token, expirationDate);
    this.user.next(user);
    this.autoLogout(expiresIn * 1000);
    localStorage.setItem('userData', JSON.stringify(user))
  }

  private handleSignInAuthentication(username: string, email: string, tokenType: string, accessToken: string, expiresIn: number) {
    const expirationDate = new Date(new Date().getTime() + expiresIn);
    const user = new User(email, username, accessToken, expirationDate);
    this.user.next(user);
    this.autoLogout(expiresIn);
    localStorage.setItem('userData', JSON.stringify(user));
    localStorage.setItem('authToken', tokenType + ' ' + accessToken);
  }


  private handleError(errorRes: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';
    if (errorRes.error) {
      errorMessage = errorRes.error.message;
    }
    if (!errorRes.error || !errorRes.error.error) {
      return throwError(errorMessage);
    }

    switch (errorRes.error.error.message) {
      case 'EMAIL_EXISTS':
        errorMessage = 'This email exists already';
        break;
      case 'EMAIL_NOT_FOUND':
        errorMessage = 'This email does not exist.';
        break;
      case 'INVALID_PASSWORD':
        errorMessage = 'The password is not correct.';
        break;
    }
    return throwError(errorMessage);
  }

  logout() {
    this.user.next(null);
    this.router.navigate(['/auth']);
    localStorage.removeItem('userData');
    localStorage.removeItem('authToken');
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
  }

  autoLogin() {
    const userData: { email: string, id: string, _token: string, _tokenExpirationDate: string } = JSON.parse(localStorage.getItem('userData'));
    if (!userData) {
      return;
    }
    const loadedUser = new User(userData.email, userData.id, userData._token, new Date(userData._tokenExpirationDate));

    if (loadedUser.token) {
      this.user.next(loadedUser);
      const expirationDuration = new Date(userData._tokenExpirationDate).getTime() -
        new Date().getTime();
      this.autoLogout(expirationDuration);
    }
  }

  autoLogout(expirationDuration: number) {
    setTimeout(() => {
      this.logout()
    }, expirationDuration)

  }
}
