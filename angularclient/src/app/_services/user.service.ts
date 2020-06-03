import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {User} from '../_model/user';

const API_URL = 'http://localhost:8080/api/test/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl: string;
  private deleteUrl: string;
  private giveAdminUrl: string;
  private giveUserUrl: string;
  private findUserUrl: string;
  private api: string;

  constructor(private http: HttpClient) {
    this.api = 'http://localhost:8080/api/users'
    this.usersUrl = this.api + '/all';
    this.deleteUrl = this.api;
    this.giveAdminUrl = this.api + '/giveAdmin';
    this.giveUserUrl = this.api + '/giveUser';
    this.findUserUrl = this.api;
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  public delete(id: number){
    return this.http.delete(this.deleteUrl+'?id='+id);
  }

  public findUser(id: number){
    return this.http.get<User>(this.findUserUrl+'?id='+id);
  }

  public giveAdmin(id: number){
    return this.http.get(this.giveAdminUrl+'?id='+id);
  }

  public giveUser(id: number){
    return this.http.get(this.giveUserUrl+'?id='+id);
  }

  public getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', { responseType: 'text' });
  }

  public getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', { responseType: 'text' });
  }
}
