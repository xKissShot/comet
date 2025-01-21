import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';  // Import RouterModule
import { FormsModule } from '@angular/forms';    // Import FormsModule
import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';  // Import RegisterComponent

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent  // Declare RegisterComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,        // Add FormsModule
    RouterModule.forRoot([   // Make sure the RouterModule is set up properly
      { path: 'register', component: RegisterComponent },  // Register route for register
      { path: '', redirectTo: '/login', pathMatch: 'full' }  // Optional: Default route
    ])
  ],
  providers: [],
  bootstrap: [AppComponent]  // Bootstrapping AppComponent
})
export class AppModule { }
