import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; // For ngModel
import { CommonModule } from '@angular/common'; // For *ngIf

import { AppComponent } from './app.component';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    FormsModule,   // Ensure FormsModule is imported
    CommonModule,  // Ensure CommonModule is imported
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
