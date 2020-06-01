import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "../_services/auth.service";

@Injectable({
  providedIn: 'root'
})
export class UserguardService implements CanActivate{

  constructor(private router: Router, private authService: AuthService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
    if (this.authService.isUserLoggedIn())
      return true;

    this.router.navigate(['home']);
    return false;
  }
}
