import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {ProfileComponent} from './profile/profile.component';
import {BoardAdminComponent} from './board-admin/board-admin.component';
import {AdminguardService} from './guards/adminguard.service';
import {UserguardService} from './guards/userguard.service';
import {BooklistComponent} from './booklist/booklist.component';
import {BookaddComponent} from './bookadd/bookadd.component';
import {MybooksComponent} from './mybooks/mybooks.component';

const routes: Routes = [
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'user/add', component: RegisterComponent, canActivate: [AdminguardService]},
  {path: 'books', component: BooklistComponent, canActivate: [UserguardService]},
  {path: 'books/add', component: BookaddComponent, canActivate: [AdminguardService]},
  {path: 'profile', component: ProfileComponent, canActivate: [UserguardService]},
  {path: 'books/my', component: MybooksComponent, canActivate: [UserguardService]},
  {path: 'admin', component: BoardAdminComponent, canActivate: [AdminguardService]},
  {path: '', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
