import { Component } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username: string = '';
  password: string = '';
  confirmPassword: string = '';

  onSubmit() {
    // Handle registration form submission
    console.log('Registering user:', this.username, this.password, this.confirmPassword);
  }
}
