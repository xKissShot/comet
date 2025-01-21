import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';  // Import RouterModule
import { RegisterComponent } from './register/register.component';  // Import the RegisterComponent

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent  // Declare RegisterComponent here
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot([  // Configure your routes here
      { path: 'register', component: RegisterComponent }
    ])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
