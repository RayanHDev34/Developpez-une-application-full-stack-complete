import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

import { Topic } from '../interfaces/topic.interface';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private readonly apiTopicsUrl = `${environment.apiUrl}/topics`;
  private readonly apiSubscriptionsUrl = `${environment.apiUrl}/subscriptions`;

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  getAll(): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.apiTopicsUrl, {
      headers: this.getAuthHeaders()
    });
  }

  unsubscribe(topicId: number): Observable<void> {
  return this.http.delete<void>(
    `${this.apiSubscriptionsUrl}/${topicId}`,
    { headers: this.getAuthHeaders() }
  );
}
}