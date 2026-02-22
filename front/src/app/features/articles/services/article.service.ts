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

  private readonly token = localStorage.getItem('token');
  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');

    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  getAll(): Observable<Article[]> {
    return this.http.get<Article[]>(this.apiUrl, {
      headers: this.getAuthHeaders()
    });
  }

  getById(id: number): Observable<ArticleDetailResponse> {
    return this.http.get<ArticleDetailResponse>(
      `${this.apiUrl}/${id}`,
      { headers: this.getAuthHeaders() }
    );
  }

  addComment(articleId: number, content: string): Observable<Comment> {
    return this.http.post<Comment>(
      `${this.apiUrl}/${articleId}/comments`,
      { content },
      { headers: this.getAuthHeaders() }
    );
  }
}