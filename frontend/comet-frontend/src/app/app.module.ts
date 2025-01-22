import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router'; // Import RouterModule
import { FormsModule } from '@angular/forms';   // Import FormsModule for ngModel
import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent // Declare LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot([
      { path: 'register', component: RegisterComponent }, // Route for RegisterComponent
      { path: 'login', component: LoginComponent },       // Route for LoginComponent
      { path: '', redirectTo: '/login', pathMatch: 'full' } // Default route
    ])
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA], // Add CUSTOM_ELEMENTS_SCHEMA
  providers: [],
  bootstrap: [AppComponent] // Bootstrap the root component
})
export class AppModule { }
