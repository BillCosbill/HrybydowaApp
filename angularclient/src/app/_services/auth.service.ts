import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TokenStorageService} from './token-storage.service';

const AUTH_API = 'http://localhost:8080/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) {
  }

  public login(credentials): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {
      username: credentials.username,
      password: credentials.password
    }, httpOptions);
  }

  public isAdminLoggedIn() {
    let roles: string[] = [];
    try {
      roles = this.tokenStorage.getUser().roles;
    } catch (e) {
      console.log(e);
    }
    return roles.includes('ROLE_ADMIN');
  }

  public isUserLoggedIn() {
    let roles: string[] = [];
    try {
      roles = this.tokenStorage.getUser().roles;
    } catch (e) {
      console.log(e);
    }
    return roles.includes('ROLE_USER') || roles.includes('ROLE_ADMIN');
  }
}
