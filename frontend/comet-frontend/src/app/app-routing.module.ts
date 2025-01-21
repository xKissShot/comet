// app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './register/register.component';  // Import RegisterComponent

const routes: Routes = [
  { path: 'register', component: RegisterComponent },  // Define the route
  { path: '', redirectTo: '/login', pathMatch: 'full' },  // Redirect to login by default
  // Add other routes as needed
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],  // Import RouterModule with routes
  exports: [RouterModule]  // Export RouterModule
})
export class AppRoutingModule { }
