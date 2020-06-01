import { Component, OnInit } from '@angular/core';
import {Book} from "../_model/book";
import {BookService} from "../_services/book.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-bookadd',
  templateUrl: './bookadd.component.html',
  styleUrls: ['./bookadd.component.css']
})
export class BookaddComponent {

  book: Book;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookService: BookService) {
    this.book = new Book();
  }

  onSubmit() {
    this.bookService.save(this.book).subscribe(result => this.goToBooksList());
  }

  goToBooksList() {
    this.router.navigate(['/books']);
  }

}
