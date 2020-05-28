import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {ProfileComponent} from "./profile/profile.component";
import {BoardUserComponent} from "./board-user/board-user.component";
import {BoardAdminComponent} from "./board-admin/board-admin.component";
import {AdminguardService} from "./guards/adminguard.service";
import {UserguardService} from "./guards/userguard.service";
import {BooklistComponent} from "./booklist/booklist.component";
import {BookaddComponent} from "./bookadd/bookadd.component";
import {MybooksComponent} from './mybooks/mybooks.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'books', component: BooklistComponent, canActivate: [UserguardService] },
  { path: 'addbook', component: BookaddComponent, canActivate: [UserguardService] },
  { path: 'profile', component: ProfileComponent, canActivate: [UserguardService] },
  { path: 'mybooks', component: MybooksComponent, canActivate: [UserguardService] },
  { path: 'user', component: BoardUserComponent, canActivate: [UserguardService] },
  { path: 'admin', component: BoardAdminComponent, canActivate: [AdminguardService] },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
