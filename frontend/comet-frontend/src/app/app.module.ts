import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms'; // Import this for reactive forms

import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent, // Declare the AppComponent
  ],
  imports: [
    BrowserModule,        // Basic Angular functionalities
    ReactiveFormsModule,  // Add ReactiveFormsModule here
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
