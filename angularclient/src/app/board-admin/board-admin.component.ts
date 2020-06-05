import {Component, OnInit} from '@angular/core';
import {UserService} from '../_services/user.service';
import {User} from '../_model/user';
import {TokenStorageService} from '../_services/token-storage.service';
import {Router} from '@angular/router';
import {AppComponent} from '../app.component';

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {

  users: User[] = [];
  searchText;

  constructor(
    private userService: UserService,
    private tokenStorageService: TokenStorageService,
    private router: Router,
    private appComponent: AppComponent
  ) {
  }

  ngOnInit() {
    this.refreshData();
  }

  makePrettyRoleName(name: string) {
    return name.substr(5).toLowerCase();
  }

  giveAdmin(id: number) {
    this.userService.giveRole(id, 'ROLE_ADMIN').subscribe(() => this.refreshData());
  }

  giveUser(id: number) {
    this.userService.giveRole(id, 'ROLE_USER').subscribe(() => {
      if (id === this.tokenStorageService.getUser().id) {
        this.router.navigate(['/home']);
        this.appComponent.logout();
      } else {
        this.refreshData();
      }
    });
  }

  delete(id: number) {
    this.userService.delete(id).subscribe(() => {
        if (id === this.tokenStorageService.getUser().id) {
          this.router.navigate(['/home']);
          this.appComponent.logout();
        } else {
          this.refreshData();
        }
      }
    );
  }

  private refreshData() {
    this.userService.findAll().subscribe(data => {
      this.users = data;
    });
  }
}
