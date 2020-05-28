import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Book} from '../model/book';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private booksUrl: string;
  private deleteUrl: string;
  private rentUrl: string;
  private returnUrl: string;

  constructor(private http: HttpClient) {
    this.booksUrl = 'http://localhost:8081/books';
    this.deleteUrl = 'http://localhost:8081/bookDelete';
    this.rentUrl = 'http://localhost:8081/rentBook';
    this.returnUrl = 'http://localhost:8081/returnBook';
  }

  public findAll(): Observable<Book[]> {
    return this.http.get<Book[]>(this.booksUrl);
  }

  public save(book: Book) {
    return this.http.post<Book>(this.booksUrl, book);
  }

  public delete(id: number){
    return this.http.get(this.deleteUrl + '?id=' + id);
  }

  public rent(idBook: number, idUser: number) {
    return this.http.get(this.rentUrl + '?idBook=' + idBook + '&idUser=' + idUser);
  }

  public return(idBook: number, idUser: number) {
    console.log(idBook);
    console.log(idUser);
    return this.http.get(this.returnUrl + '?idBook=' + idBook + '&idUser=' + idUser);
  }


}
