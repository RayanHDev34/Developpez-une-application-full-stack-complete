import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MainLayoutComponent } from 'src/app/layout/main-layout/main-layout.component';

import { ArticleService } from '../../../services/article.service';
import { CreateArticleRequest } from '../interfaces/create-article-request.interface';
import { Topic } from '../../../interfaces/topic.interface';
import { TopicService } from '../../../services/topic.service';

@Component({
  selector: 'app-create-article',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MainLayoutComponent
  ],
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss']
})
export class CreateArticleComponent implements OnInit {

  form: FormGroup;
  topics: Topic[] = [];
  loading = false;
  error?: string;

  constructor(
    private fb: FormBuilder,
    private location: Location,
    private topicService: TopicService,
    private articleService: ArticleService
  ) {
    this.form = this.fb.group({
      topicId: ['', Validators.required],
      title: ['', Validators.required],
      content: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadTopics();
  }

  loadTopics(): void {
    this.loading = true;

    this.topicService.getAll().subscribe({
      next: (data) => {
        this.topics = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Erreur lors du chargement des topics';
        this.loading = false;
      }
    });
  }

  goBack(): void {
    this.location.back();
  }

 submit(): void {
  if (this.form.invalid) {
    this.form.markAllAsTouched();
    return;
  }

  const payload: CreateArticleRequest = this.form.value;

  this.articleService.createArticle(payload)
    .subscribe({
      next: () => {
        // Retour à la page précédente
        this.location.back();
      },
      error: () => {
        console.error('Erreur lors de la création');
      }
    });
  }
}