import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../environments/environment.prod";
import {EnvService} from "../../../../env.service";

@Component({
  selector: 'app-user-menu',
  templateUrl: './user-menu.component.html',
  styleUrls: ['./user-menu.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class UserMenuComponent implements OnInit {
  urlPM: any;
  public thumbnailphoto;
  username: string;
  profiles: string;
  public profile;
  public roles;

  constructor(private env :EnvService) {
    this.thumbnailphoto=localStorage.getItem("thumbnailphoto")
    this.username=sessionStorage.getItem("profileUser")
    this.urlPM=this.env.apiUrlprofile ;
  }

  ngOnInit() {
  }
  signOut() {
    localStorage.clear();
    sessionStorage.clear();
   //clearMenu();
    // window.location.replace('');
  }

}
