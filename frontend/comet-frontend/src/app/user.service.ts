// user.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './user.model';  // Make sure you have the User model defined

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = '/api/users';  // Make sure the API URL is correct

  constructor(private http: HttpClient) {}

  // Get all users
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  // Other methods for adding users, etc.
}
