import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Topic } from '../interfaces/topic.interface';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private readonly apiTopicsUrl = `${environment.apiUrl}/topics`;
  private readonly apiSubscriptionsUrl = `${environment.apiUrl}/subscriptions`;
  
  constructor(private http: HttpClient) {}

  getAll(): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.apiTopicsUrl, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    });
  }
  subscribe(topicId: number): Observable<void> {
    return this.http.post<void>(`${this.apiSubscriptionsUrl}/${topicId}`, {}, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    });
  }
}