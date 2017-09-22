import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'asm-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  loginOrOut: string = "Login";
  assembleLink: string = "/login";

  constructor() {
  }

  ngOnInit() {
  }

}
