import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Topic } from '../interfaces/topic.interface';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private readonly apiUrl = `${environment.apiUrl}/topics`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Topic[]> {

    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.get<Topic[]>(this.apiUrl, { headers });
  }
}