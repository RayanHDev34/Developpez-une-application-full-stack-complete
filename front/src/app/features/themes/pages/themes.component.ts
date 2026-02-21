import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainLayoutComponent } from '../../../layout/main-layout/main-layout.component';
import { Topic } from '../interfaces/topic.interface';
import { TopicService } from '../services/topic.service';

@Component({
  selector: 'app-themes-page',
  standalone: true,
  imports: [
    CommonModule,
    MainLayoutComponent
  ],
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss']
})
export class ThemesComponent implements OnInit {

  topics: Topic[] = [];
  loading = false;
  error?: string;

  constructor(private topicService: TopicService) {}

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
        this.error = 'Erreur lors du chargement';
        this.loading = false;
      }
    });
  }
  subscribe(topic: Topic): void {
    this.topicService.subscribe(topic.id).subscribe({
      next: () => {
        topic.subscribed = true;
      },
      error: () => {
        this.error = 'Erreur lors de l’abonnement';
      }
    });
  }
}