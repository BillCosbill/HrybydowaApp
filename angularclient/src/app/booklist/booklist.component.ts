import { Component, OnInit } from '@angular/core';
import {Book} from '../model/book';
import {BookService} from '../services/book.service';
import {TokenStorageService} from '../services/token-storage.service';

@Component({
  selector: 'app-booklist',
  templateUrl: './booklist.component.html',
  styleUrls: ['./booklist.component.css']
})
export class BooklistComponent implements OnInit {

  private roles: string[];
  books: Book[];
  adminLogged = false;
  isLoggedIn = false;
  currentUser: any;

  constructor(private bookService: BookService, private tokenStorageService: TokenStorageService) { }

  ngOnInit() {
    this.currentUser = this.tokenStorageService.getUser();

    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      this.roles = this.currentUser.roles;
      this.adminLogged = this.roles.includes('ROLE_ADMIN');
    }

    this.refreshData();
  }

  deleteBook(id: number) {
    this.bookService.delete(id).subscribe(result => this.refreshData());
  }


  private refreshData() {
    this.bookService.findAll().subscribe(data => {
      this.books = data;
    });
  }

  rent(idBook: number) {
    this.bookService.rent(idBook, this.currentUser.id).subscribe(result => this.refreshData());
    this.tokenStorageService.saveUser(this.currentUser);
    this.tokenStorageService.getUser();
  }
}
