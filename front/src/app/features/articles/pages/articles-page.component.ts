import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Article } from '../../../interfaces/articles.interface';
import { MainLayoutComponent } from '../../../layout/main-layout/main-layout.component';

@Component({
  selector: 'app-articles-page',
  standalone: true,
  imports: [
    CommonModule,
    MainLayoutComponent
  ],
  templateUrl: './articles-page.component.html',
  styleUrls: ['./articles-page.component.scss']
})
export class ArticlesComponent {
  constructor(private router: Router) {}
  sortOrder: 'desc' | 'asc' = 'desc';

  readonly mockArticles: Article[] = [
    {
      id: 1,
      title: 'Comprendre les bases d’Angular',
      content:
        'Angular est un framework puissant permettant de créer des applications web robustes et maintenables...',
      author: 'Orlando Espinoza',
      createdAt: '12/09/2024'
    },
    {
      id: 2,
      title: 'Architecture feature-based : pourquoi ça change tout',
      content:
        'Organiser une application par fonctionnalités permet de gagner en lisibilité et en évolutivité...',
      author: 'John Doe',
      createdAt: '10/09/2024'
    },
    {
      id: 3,
      title: 'Les bonnes pratiques TypeScript',
      content:
        'Le typage strict en TypeScript réduit les bugs et améliore la qualité globale du code...',
      author: 'Jane Smith',
      createdAt: '08/09/2024'
    },
    {
      id: 4,
      title: 'Pourquoi séparer logique et UI',
      content:
        'Une bonne séparation des responsabilités permet de maintenir une application sur le long terme...',
      author: 'Alex Martin',
      createdAt: '05/09/2024'
    },
    {
      id: 5,
      title: 'Comprendre le cycle de vie Angular',
      content:
        'Le cycle de vie d’un composant Angular permet de mieux comprendre quand et comment agir...',
      author: 'Sarah Lee',
      createdAt: '03/09/2024'
    },
    {
      id: 6,
      title: 'Clean Code : les règles essentielles',
      content:
        'Un code propre est un code facile à lire, à comprendre et à maintenir...',
      author: 'Michael Brown',
      createdAt: '01/09/2024'
    }
  ];
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
    return [...this.mockArticles].sort((a, b) => {
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
