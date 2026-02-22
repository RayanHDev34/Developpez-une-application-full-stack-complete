import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Article } from '../../../interfaces/articles.interface';
import { ArticleDetailResponse } from '../interfaces/article-detail.interface';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private readonly apiUrl = `${environment.apiUrl}/articles`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Article[]> {

    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.get<Article[]>(this.apiUrl, { headers });
  }
  getById(id: number): Observable<ArticleDetailResponse> {

    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.get<ArticleDetailResponse>(`${this.apiUrl}/${id}`, { headers });
  }
}