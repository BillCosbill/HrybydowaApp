import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Book} from '../_model/book';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private booksUrl: string;

  constructor(private http: HttpClient) {
    this.booksUrl = 'http://localhost:8080/api/books';
  }

  public findAll(): Observable<Book[]> {
    return this.http.get<Book[]>(this.booksUrl + '/all');
  }

  public addBook(book: Book) {
    return this.http.post<Book>(this.booksUrl + '/add', book);
  }

  public delete(id: number) {
    return this.http.delete(this.booksUrl + '/delete' + '?id=' + id);
  }

  public rent(bookId: number, userId: number) {
    return this.http.put(this.booksUrl + '/rent', {
      userId,
      bookId
    });
  }

  public return(bookId: number, userId: number) {
    return this.http.put(this.booksUrl + '/return', {
      userId,
      bookId
    });
  }


}
