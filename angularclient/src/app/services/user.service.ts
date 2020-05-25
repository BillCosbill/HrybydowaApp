import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {User} from '../model/user';

const API_URL = 'http://localhost:8081/api/test/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl: string;
  private deleteUrl: string;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8081/users';
    this.deleteUrl = 'http://localhost:8081/userDelete';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  // TODO NIE DZIAŁA USUWANIE UŻYTKOWNIKÓW
  public delete(id: number){
    console.log('usuwamy');
    console.log(id);
    return this.http.get<User>(this.deleteUrl+'?id='+id);
  }

  public getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', { responseType: 'text' });
  }

  public getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }
}
