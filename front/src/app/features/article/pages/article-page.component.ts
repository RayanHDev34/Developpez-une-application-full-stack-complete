import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { Comment } from '../interfaces/comment.interface';
import { Article } from '../../../interfaces/articles.interface';
import { MainLayoutComponent } from 'src/app/layout/main-layout/main-layout.component';

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
export class ArticleComponent {

  article?: Article;
  articleId!: number;

  comments: Comment[] = [
    { id: 1, author: 'username', content: 'contenu du commentaire' }
  ];

  commentForm: FormGroup;

  constructor(
    private location: Location,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {

    // 1️⃣ Récupération de l'ID depuis l'URL
    this.articleId = Number(this.route.snapshot.paramMap.get('id'));

    // 2️⃣ Tentative de récupération de l'article via state
    const navigation = this.router.getCurrentNavigation();
    this.article = navigation?.extras.state?.['article'];

    // 3️⃣ Fallback MVP si refresh / accès direct
    if (!this.article) {
      console.warn('Article non trouvé dans le state, fallback MVP');
      // 👉 MVP : retour à la liste
      this.router.navigate(['/articles']);
    }

    this.commentForm = this.fb.group({
      content: ['', Validators.required]
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

    this.comments.push({
      id: Date.now(),
      author: 'username',
      content: this.commentForm.value.content
    });

    this.commentForm.reset();
  }
}
