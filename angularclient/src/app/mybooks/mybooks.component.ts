import { Component, OnInit } from '@angular/core';
import {Book} from '../_model/book';
import {BookService} from '../_services/book.service';
import {TokenStorageService} from '../_services/token-storage.service';
import {UserService} from '../_services/user.service';
import {User} from '../_model/user';

@Component({
  selector: 'app-mybooks',
  templateUrl: './mybooks.component.html',
  styleUrls: ['./mybooks.component.css']
})
export class MybooksComponent implements OnInit {

  books: Book[];
  user: User;
  currentUser: any;
  userId: number;

  searchText;

  constructor(private bookService: BookService, private tokenStorageService: TokenStorageService, private userService: UserService) { }

  ngOnInit() {
    this.currentUser = this.tokenStorageService.getUser();
    this.userId = this.currentUser.id;
    this.refreshData();
  }

  return(id: number) {
    this.bookService.return(id, this.userId).subscribe(result => this.refreshData());
  }

  private refreshData() {
    this.userService.findUser(this.userId).subscribe(result => {
      this.user = result;
      this.books = this.user.books;
    });
  }
}
