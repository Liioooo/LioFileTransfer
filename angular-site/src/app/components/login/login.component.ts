import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormControl, FormGroup, Validators} from '@angular/forms';
import {Title} from '@angular/platform-browser';
import {UserService} from '../../services/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public loginForm: FormGroup;
  public submitted = false;

  constructor(private title: Title, private user: UserService, private router: Router) { }

  ngOnInit() {
      this.title.setTitle('LioFileTransfer - Login');
      this.loginForm = new FormGroup({
         username: new FormControl('', Validators.required),
         password: new FormControl('', Validators.required)
      });
  }

    public get form() { return this.loginForm.controls; }

    public handleSubmitClick(): void {
        this.submitted = true;

        if (this.loginForm.invalid) {
            return;
        }

        const username = this.loginForm.controls.username.value;
        const password = this.loginForm.controls.password.value;

        this.user.login(username, password).subscribe(data => {
           if(data['error'] === 'invalid') {
               this.loginForm.controls.password.setErrors({invalidPW: true})
           } else {
               this.router.navigate(['/files']);
           }
        });
    }
}
