import {Component, OnInit} from '@angular/core';
import {UserService} from '../_services/user.service';
import {User} from '../_model/user';

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {

  users: User[] = [];

  searchText;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.refreshData();
  }

  makePrettyRoleName(name: string) {
    return name.substr(5).toLowerCase();
  }

  private checkIfAdmins() {
    this.users.forEach(x => {
      x.isAdmin = Object.values(x.roles[0]).includes('ROLE_ADMIN');
    });
  }

  giveAdmin(id: number) {
    this.userService.giveRole(id, 'ROLE_ADMIN').subscribe(result => this.refreshData());
  }

  giveUser(id: number) {
    this.userService.giveRole(id, 'ROLE_USER').subscribe(result => this.refreshData());
  }

  delete(id: number) {
    this.userService.delete(id).subscribe(result => this.refreshData());
  }

  private refreshData() {
    this.userService.findAll().subscribe(data => {
      this.users = data;
      console.log('user_roles:', this.users[0].roles);
    });
  }
}
