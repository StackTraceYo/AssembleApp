import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'asm-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  loginOrOut: string = "Login"

  constructor() {
  }

  ngOnInit() {
  }

}
