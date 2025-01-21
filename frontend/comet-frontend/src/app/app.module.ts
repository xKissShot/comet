import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';  // This imports CommonModule automatically
import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule  // No need for CommonModule here, as BrowserModule includes it.
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
