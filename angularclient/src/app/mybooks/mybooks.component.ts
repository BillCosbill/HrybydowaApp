import { Component, OnInit } from '@angular/core';
import {Book} from '../model/book';
import {BookService} from '../services/book.service';
import {TokenStorageService} from '../services/token-storage.service';
import {UserService} from '../services/user.service';
import {User} from '../model/user';

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
