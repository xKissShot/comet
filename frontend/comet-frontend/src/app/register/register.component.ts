// register.component.ts
import { Component } from '@angular/core';
import { Router } from '@angular/router';  // Import Router to navigate on successful registration

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username: string = '';
  password: string = '';
  confirmPassword: string = '';

  constructor(private router: Router) {}

  onSubmit() {
    if (this.password === this.confirmPassword) {
      // Call your backend registration service here
      console.log('Registration successful!');
      // After successful registration, redirect to the login page
      this.router.navigate(['/login']);
    } else {
      // Handle password mismatch error
      console.log('Passwords do not match');
    }
  }
}
