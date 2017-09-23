import {Component, OnInit} from '@angular/core';
import {UserService} from "../user-auth/user-service";

@Component({
  selector: 'asm-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  loginOrOut: string = "Login";
  assembleLink: string = "/login";

  constructor(private userService: UserService,) {
  }

  ngOnInit() {

    this.userService.getUser()
      .subscribe(user => {
          if (user.authenticated) {
            this.loginOrOut = "Logout";
          }
        },
        err => {
          this.loginOrOut = "Logout";
          console.log(err);
        });
  }

}
