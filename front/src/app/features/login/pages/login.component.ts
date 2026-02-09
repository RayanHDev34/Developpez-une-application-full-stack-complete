import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule, Location } from '@angular/common';


import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

import { AuthService } from '../../login/services/auth.service';
import { AuthLayoutComponent } from '../../../layout/auth-layout/auth-layout.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    AuthLayoutComponent
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  form: FormGroup;
  constructor(
    private fb: FormBuilder,
    private location: Location,
    private authService: AuthService,
    private router: Router
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required]],
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

  const credentials = this.form.value;

  this.authService.login(credentials).subscribe({
    next: (user) => {
      console.log('Utilisateur connecté', user);
       this.router.navigate(['/articles']);
    },
    error: (err) => {
      if (err.status === 401) {
        console.error('Identifiants invalides');
      } else {
        console.error('Erreur serveur');
      }
    }
  });
}
}
