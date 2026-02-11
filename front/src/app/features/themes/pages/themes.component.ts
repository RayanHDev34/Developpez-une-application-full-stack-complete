import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainLayoutComponent } from '../../../layout/main-layout/main-layout.component';
import { Theme } from '../interfaces/theme.interface';

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
export class ThemesComponent {

  themes: Theme[] = [
    {
      id: 1,
      title: 'Angular',
      description: 'Description: lorem ipsum is simply dummy text of the printing and typesetting industry.',
      subscribed: false
    },
    {
      id: 2,
      title: 'React',
      description: 'Description: lorem ipsum is simply dummy text of the printing and typesetting industry.',
      subscribed: false
    },
    {
      id: 3,
      title: 'Vue',
      description: 'Description: lorem ipsum is simply dummy text of the printing and typesetting industry.',
      subscribed: true
    },
    {
      id: 4,
      title: 'Node.js',
      description: 'Description: lorem ipsum is simply dummy text of the printing and typesetting industry.',
      subscribed: true
    }
  ];

  subscribe(theme: Theme): void {
    theme.subscribed = true;
  }
}
