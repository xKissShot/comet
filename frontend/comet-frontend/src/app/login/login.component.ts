import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  onSubmit() {
    // Logic to authenticate the user
    console.log('Username:', this.username);
    console.log('Password:', this.password);
    alert('Login submitted!'); // Replace with actual authentication logic
  }
}
