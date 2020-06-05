import {Component, OnInit} from '@angular/core';
import {AuthService} from '../_services/auth.service';
import {UserService} from '../_services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  form: any = {};
  wasCreationSuccessful = false;
  hasCreationFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService,
              private userService: UserService
  ) {
  }

  ngOnInit() {
  }

  onSubmit() {
    console.log('this.form: ', this.form);
    this.userService.add(this.form).subscribe(
      data => {
        console.log('data:', data);
        this.wasCreationSuccessful = true;
        this.hasCreationFailed = false;
      },
      err => {
        this.errorMessage = err.error.message;
        this.hasCreationFailed = true;
      }
    );
  }
}
