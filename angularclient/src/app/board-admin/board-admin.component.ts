import { Component, OnInit } from '@angular/core';
import {UserService} from "../services/user.service";
import {Book} from '../model/book';
import {User} from '../model/user';
import {applySourceSpanToExpressionIfNeeded} from '@angular/compiler/src/output/output_ast';

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {

  users: User[] = [];

  searchText;

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
      x.isAdmin = Object.values(x.roles[0]).includes('ROLE_ADMIN');
    });
  }

  giveAdmin(id: number) {
    this.userService.giveAdmin(id).subscribe(result => this.refreshData());
  }

  giveUser(id: number) {
    this.userService.giveUser(id).subscribe(result => this.refreshData());
  }

  delete(id: number) {
    this.userService.delete(id).subscribe(result => this.refreshData());
  }
}
