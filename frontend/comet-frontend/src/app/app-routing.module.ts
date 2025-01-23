import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },  // This should load LoginComponent at /login
  { path: 'register', component: RegisterComponent },  // This should load RegisterComponent at /register
  { path: '**', redirectTo: '/login' },  // Fallback to login if route is invalid
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],  // Ensure routes are properly configured
  exports: [RouterModule],  // Export RouterModule
})
export class AppRoutingModule {}
