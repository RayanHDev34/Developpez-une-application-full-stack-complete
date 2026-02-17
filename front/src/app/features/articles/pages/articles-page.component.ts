import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ArticleService } from '../services/article.service';
import { Article } from '../../../interfaces/articles.interface';
import { MainLayoutComponent } from '../../../layout/main-layout/main-layout.component';

@Component({
  selector: 'app-articles-page',
  standalone: true,
  imports: [
    CommonModule,
    MainLayoutComponent,
  ],
  templateUrl: './articles-page.component.html',
  styleUrls: ['./articles-page.component.scss']
})
export class ArticlesComponent {
  constructor(
    private router: Router,
    private articleService: ArticleService
  ) {}
  
  articles: Article[] = [];
  loading = false;
  error?: string;

  sortOrder: 'desc' | 'asc' = 'desc';

  ngOnInit(): void {
    this.loadArticles();
  }

  loadArticles(): void {
    this.loading = true;
    this.error = undefined;

    this.articleService.getAll().subscribe({
      next: (data) => {
        this.articles = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des articles';
        this.loading = false;
      }
    });
  }
  goToArticle(article: Article): void {
  this.router.navigate(
    ['/articles', article.id],
    {
      state: { article }
    }
  );
  }
  goToCreateArticle(): void {
    console.log('Navigating to create article page');
    this.router.navigate(['/articles/new']);
  }
  get sortedArticles(): Article[] {
    return [...this.articles].sort((a, b) => {
      const dateA = new Date(a.createdAt).getTime();
      const dateB = new Date(b.createdAt).getTime();

      return this.sortOrder === 'desc'
        ? dateB - dateA
        : dateA - dateB;
    });
  }
  toggleSort(): void {
    this.sortOrder = this.sortOrder === 'desc' ? 'asc' : 'desc';
  }
}
