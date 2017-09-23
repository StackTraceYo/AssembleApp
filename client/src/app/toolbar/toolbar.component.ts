import {Component, OnInit} from '@angular/core';
import {UserService} from "../user-auth/user-service";
import {Router} from "@angular/router";

@Component({
  selector: 'asm-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  loginOrOut: string = "Login";
  loggedIn: boolean = false;
  assembleLink: string = "/login";

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit() {

    this.userService.getUser()
      .subscribe(user => {
          if (user.authenticated) {
            this.loginOrOut = "Logout";
            this.loggedIn = true;
          }
        },
        err => {
          this.loginOrOut = "Logout";
          this.loggedIn = false;
          console.log(err);
        });
  }

  toggleUserSignedIn() {
    this.loggedIn ? this.logout() : this.login();
  }

  logout() {
    this.userService.removeUser()
    this.gotoLogin()
  }

  login() {
    this.gotoLogin()
  }

  gotoLogin() {
    this.router.navigate(['/login']);
  }

}
