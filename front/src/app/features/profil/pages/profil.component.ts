import { Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { MainLayoutComponent } from 'src/app/layout/main-layout/main-layout.component';
import { Topic } from '../../../interfaces/topic.interface';
import { TopicService } from '../../../services/topic.service';
@Component({
  selector: 'app-profil-page',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MainLayoutComponent
  ],
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.scss']
})
export class ProfilComponent implements OnInit {

  form: FormGroup;
  subscriptions: Topic[] = [];
  loading = false;
  error?: string;

  constructor(
    private fb: FormBuilder,
    private topicService: TopicService
  ) {
    this.form = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['']
    });
  }

  ngOnInit(): void {
    this.loadSubscriptions();
  }

  loadSubscriptions(): void {
    this.loading = true;

    this.topicService.getAll().subscribe({
      next: (topics) => {
        this.subscriptions = topics.filter(t => t.subscribed);
        this.loading = false;
      },
      error: () => {
        this.error = 'Erreur lors du chargement';
        this.loading = false;
      }
    });
  }

  save(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    console.log('Profil mis à jour :', this.form.value);
  }

 unsubscribe(topic: Topic): void {

    this.topicService.unsubscribe(topic.id)
      .subscribe({
        next: () => {
          this.subscriptions = this.subscriptions.filter(
            t => t.id !== topic.id
          );
        },
        error: () => {
          this.error = 'Erreur lors de la désinscription';
        }
      });
  }
}
