import { Component, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common'; // For *ngIf
import { FormsModule } from '@angular/forms';   // For ngModel

@Component({
  selector: 'app-root',
  standalone: true, // Ensure this is set if using standalone components
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [CommonModule, FormsModule], // Add required modules here
  schemas: [CUSTOM_ELEMENTS_SCHEMA], // Add CUSTOM_ELEMENTS_SCHEMA here
})
export class AppComponent {
  username: string = '';
  password: string = '';
  confirmPassword: string = '';
  touched: boolean = false;

  onSubmit() {
    alert('Form submitted successfully!');
  }
}
