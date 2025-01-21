import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';  // Import HttpClientModule

@NgModule({
  declarations: [
    AppComponent
    // other components
  ],
  imports: [
    BrowserModule,
    HttpClientModule  // Add HttpClientModule here
    // other modules
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
