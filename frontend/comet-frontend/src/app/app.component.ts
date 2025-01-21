// app.component.ts
import { Component, OnInit } from '@angular/core';
import { UserService } from './user.service';
import { User } from './user.model';  // Ensure the User model exists

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  users: User[] = [];
  loading = false;
  error: string = '';

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loading = true;
    this.userService.getUsers().subscribe(
      (data: User[]) => {
        this.users = data;
        this.loading = false;
      },
      (err) => {
        this.error = 'Failed to load users';
        this.loading = false;
      }
    );
  }
}
