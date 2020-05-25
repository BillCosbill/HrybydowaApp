import { Component, OnInit } from '@angular/core';
import {UserService} from "../services/user.service";
import {Book} from '../model/book';
import {User} from '../model/user';

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {

  users: User[] = [];
  role: string;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.refreshData();
  }

  // TODO ZROBIĆ COŚ ŻEBY WYPISYWAŁO ROLE UŻYTKOWNIKA W TABELI W HTMLU
  private refreshData() {
    this.userService.findAll().subscribe(data => {
      this.users = data;
    });
  }

  private checkIfAdmins() {
    this.users.forEach(x => {
      if (Object.values(x.roles[0]).includes('ROLE_ADMIN')) {
        x.isAdmin = true;
      } else {
        x.isAdmin = false;
      }
    });
  }

  giveAdmin(id: string) {

  }

  // TODO NIE DZIAŁA USUWANIE UŻYTKOWNIKÓW
  delete(id: number) {
    this.userService.delete(id);
  }
}
