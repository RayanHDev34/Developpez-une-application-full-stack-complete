import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { User } from 'src/app/interfaces/user.interface';
import { environment } from 'src/environments/environment';
import { LoginRequest } from '../interfaces/loginRequest.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly apiUrl = `${environment.apiUrl}/auth`;

  constructor(private http: HttpClient) {}

  login(payload: LoginRequest): Observable<User> {
    return this.http
      .post<User>(`${this.apiUrl}/login`, payload)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: any) {
    return throwError(() => error);
  }
}
