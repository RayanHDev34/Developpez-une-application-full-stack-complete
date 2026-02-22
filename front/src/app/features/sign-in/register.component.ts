import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { Router } from '@angular/router';

import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { AuthLayoutComponent } from '../../layout/auth-layout/auth-layout.component';
import { AuthService } from 'src/app/services/auth.service';
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

@Component({
  selector: 'app-register',
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
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  loading = false;
  form!: FormGroup;
  error?: string;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private location: Location
  ) {}
  strongPasswordValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {

      const value = control.value || '';

      const hasMinLength = value.length >= 8;
      const hasLowerCase = /[a-z]/.test(value);
      const hasUpperCase = /[A-Z]/.test(value);
      const hasNumber = /[0-9]/.test(value);
      const hasSpecialChar = /[^A-Za-z0-9]/.test(value);

      const valid = hasMinLength && hasLowerCase && hasUpperCase && hasNumber && hasSpecialChar;

      return !valid ? { strongPassword: true } : null;
    };
  }
  ngOnInit(): void {
    this.form = this.fb.group({
    username: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, this.strongPasswordValidator()]],
  });
  }

  submit(): void {
  if (this.form.invalid) {
    this.form.markAllAsTouched();
    return;
  }

  this.loading = true;
  this.error = undefined;

  const payload = this.form.value;

  this.authService.register(payload).subscribe({
    next: (response) => {
      this.loading = false;

      if (response?.token) {
        localStorage.setItem('token', response.token);
        localStorage.setItem('user', JSON.stringify(response.user));
      }

      this.router.navigate(['/login']);
    },
    error: (err) => {
      this.loading = false;
      this.error = err.error?.message || 'Erreur lors de l’inscription';
    }
  });
}
  goBack(): void {
    this.location.back();
  }

}
