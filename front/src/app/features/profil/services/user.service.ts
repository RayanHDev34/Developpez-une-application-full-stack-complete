import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly apiUrl = `${environment.apiUrl}/users`;

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  getCurrentUser(): Observable<User> {
    return this.http.get<User>(
      `${this.apiUrl}/me`,
      { headers: this.getAuthHeaders() }
    );
  }

  updateUser(payload: Partial<User> & { password?: string }): Observable<User> {
    return this.http.put<User>(
      `${this.apiUrl}/me`,
      payload,
      { headers: this.getAuthHeaders() }
    );
  }
}