import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { Article } from '../../interfaces/articles.interface';
import { Comment } from '../articles/interfaces/article-detail.interface';
import { MainLayoutComponent } from 'src/app/layout/main-layout/main-layout.component';
import { ArticleDetailResponse } from '../articles/interfaces/article-detail.interface';
import { ArticleService } from '../articles/services/article.service';

@Component({
  selector: 'app-article-page',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MainLayoutComponent
  ],
  templateUrl: './article-page.component.html',
  styleUrls: ['./article-page.component.scss']
})
export class ArticleComponent implements OnInit {

  article?: Article;
  articleId!: number;

  comments: Comment[] = [];
  loading = false;
  error?: string;

  commentForm: FormGroup;

  constructor(
    private location: Location,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private articleService: ArticleService,
  ) {
    this.commentForm = this.fb.group({
      content: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.articleId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadArticle();
  }

  loadArticle(): void {
    this.loading = true;

    this.articleService.getById(this.articleId).subscribe({
      next: (data: ArticleDetailResponse) => {
        this.article = data.article;
        this.comments = data.comments;
        this.loading = false;
      },
      error: () => {
        this.error = 'Erreur lors du chargement';
        this.loading = false;
      }
    });
  }

  goBack(): void {
    this.location.back();
  }

  submitComment(): void {
  if (this.commentForm.invalid) {
    this.commentForm.markAllAsTouched();
    return;
  }

  const content = this.commentForm.value.content;

  this.articleService.addComment(this.articleId, content)
    .subscribe({
      next: () => {
        this.comments.unshift({
          id: Date.now(),
          content: content,
          authorName: 'Moi', 
          createdAt: new Date().toISOString()
        });

        this.commentForm.reset();
      },
      error: () => {
        this.error = 'Erreur lors de l’envoi du commentaire';
      }
    });
  }
}