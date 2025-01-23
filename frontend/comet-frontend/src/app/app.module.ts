import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';  // Ensure AppComponent is imported
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AppRoutingModule } from './app-routing.module';  // Import the AppRoutingModule

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,  // Ensure LoginComponent is declared here
    RegisterComponent, // Ensure RegisterComponent is declared here
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,  // Add AppRoutingModule here instead of RouterModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent],  // Make sure AppComponent is bootstrapped
})
export class AppModule {}
