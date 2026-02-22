import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MainLayoutComponent } from 'src/app/layout/main-layout/main-layout.component';

import { User } from '../interfaces/user.interface';
import { UserService } from '../services/user.service';
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
  success?: string;

  constructor(
    private fb: FormBuilder,
    private topicService: TopicService,
    private userService: UserService
  ) {
    this.form = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['']
    });
  }

  ngOnInit(): void {
    this.loadUser();
    this.loadSubscriptions();
  }

  // 🔹 Charger infos utilisateur
  loadUser(): void {
    this.userService.getCurrentUser().subscribe({
      next: (user: User) => {
        this.form.patchValue({
          username: user.username,
          email: user.email
        });
      },
      error: () => {
        this.error = 'Erreur lors du chargement du profil';
      }
    });
  }

  loadSubscriptions(): void {
    this.loading = true;

    this.topicService.getAll().subscribe({
      next: (topics) => {
        this.subscriptions = topics.filter(t => t.subscribed);
        this.loading = false;
      },
      error: () => {
        this.error = 'Erreur lors du chargement des abonnements';
        this.loading = false;
      }
    });
  }

  save(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const payload = this.form.value;

    this.userService.updateUser(payload).subscribe({
      next: () => {
        this.success = 'Profil mis à jour avec succès';
        this.form.patchValue({ password: '' });
      },
      error: () => {
        this.error = 'Erreur lors de la mise à jour';
      }
    });
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