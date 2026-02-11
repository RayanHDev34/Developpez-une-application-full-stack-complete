import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { MainLayoutComponent } from 'src/app/layout/main-layout/main-layout.component';
import { Subscription } from '../interfaces/subscription.interface';

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
export class ProfilComponent {

  form: FormGroup;

  subscriptions: Subscription[] = [
    {
      id: 1,
      title: 'Angular',
      description: 'Description: lorem ipsum is simply dummy text of the printing and typesetting industry.'
    },
    {
      id: 2,
      title: 'React',
      description: 'Description: lorem ipsum is simply dummy text of the printing and typesetting industry.'
    }
  ];

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      username: ['Username', Validators.required],
      email: ['email@email.fr', [Validators.required, Validators.email]],
      password: ['']
    });
  }

  save(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    console.log('Profil mis à jour :', this.form.value);
  }

  unsubscribe(subscription: Subscription): void {
    this.subscriptions = this.subscriptions.filter(
      sub => sub.id !== subscription.id
    );
  }
}
