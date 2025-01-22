import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component'; // Import the AppComponent
import { appConfig } from './app/app.config'; // Import appConfig if necessary

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err)); // Catch any errors during bootstrapping
