import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  registrationForm: FormGroup;

  constructor(private fb: FormBuilder) {
    // Initialize the form using FormBuilder
    this.registrationForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {}

  onSubmit() {
    if (this.registrationForm.valid) {
      const { username, password, confirmPassword } = this.registrationForm.value;

      if (password !== confirmPassword) {
        console.error('Passwords do not match');
        return;
      }

      console.log('Form Submitted:', { username, password });
    } else {
      console.error('Form is invalid');
    }
  }
}
