import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Article } from '../../../interfaces/articles.interface';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private readonly apiUrl = `${environment.apiUrl}/articles`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Article[]> {
    return this.http.get<Article[]>(this.apiUrl);
  }
}
