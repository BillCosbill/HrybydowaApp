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

  constructor(private http: HttpClient) {
    this.booksUrl = 'http://localhost:8081/books';
    this.deleteUrl = 'http://localhost:8081/bookDelete';
  }

  public findAll(): Observable<Book[]> {
    return this.http.get<Book[]>(this.booksUrl);
  }

  public save(book: Book) {
    return this.http.post<Book>(this.booksUrl, book);
  }

  public delete(id: number){
    return this.http.get(this.deleteUrl+'?id='+id);
  }


}
