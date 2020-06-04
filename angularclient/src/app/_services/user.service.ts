import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from '../_model/user';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private usersUrl: string;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080/api/users';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl + '/all');
  }

  public delete(id: number) {
    return this.http.delete(this.usersUrl + '?id=' + id);
  }

  public findUser(id: number) {
    return this.http.get<User>(this.usersUrl + '?id=' + id);
  }

  public giveRole(id: number, role: string) {
    return this.http.put(this.usersUrl + '/give', {
      id,
      role
    });
  }

  public getPublicContent(): Observable<any> { // todo to delete
    return this.http.get(this.usersUrl + 'all', {responseType: 'text'});
  }

  public getUserBoard(): Observable<any> { // todo to delete
    return this.http.get(this.usersUrl + 'user', {responseType: 'text'});
  }

  public register(user): Observable<any> {
    console.log('user: ', user);
    return this.http.post(this.usersUrl + '/add', {
      username: user.username,
      email: user.email,
      password: user.password
    }, httpOptions);
  }
}
