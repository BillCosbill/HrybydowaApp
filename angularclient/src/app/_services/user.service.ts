import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from '../_model/user';

const API_URL = 'http://localhost:8080/api/test/';
const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl: string;
  private deleteUrl: string;
  private giveAdminUrl: string;
  private giveUserUrl: string;
  private findUserUrl: string;
  private giveUrl: string;
  private api: string;

  constructor(private http: HttpClient) {
    this.api = 'http://localhost:8080/api/users';
    this.usersUrl = this.api + '/all';
    this.deleteUrl = this.api;
    this.giveUrl = this.api + '/give';
    this.findUserUrl = this.api;
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  public delete(id: number) {
    return this.http.delete(this.deleteUrl + '?id=' + id);
  }

  public findUser(id: number) {
    return this.http.get<User>(this.findUserUrl + '?id=' + id);
  }

  public giveRole(id: number, role: string) {
    return this.http.put(this.giveUrl, {
      id,
      role
    });
  }

  public getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', {responseType: 'text'});
  }

  public getUserBoard(): Observable<any> {
    return this.http.get(API_URL + 'user', {responseType: 'text'});
  }

  public register(user): Observable<any> {
    const url = 'http://localhost:8080/user/add';
    return this.http.post(url, {
      username: user.username,
      email: user.email,
      password: user.password
    }, httpOptions);
  }
}
