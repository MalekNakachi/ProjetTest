import { Component, OnInit, ViewEncapsulation, HostListener } from '@angular/core';
import { trigger,  state,  style, transition, animate } from '@angular/animations';
import { AppSettings } from '../../../app.settings';
import { Settings } from '../../../app.settings.model';
import { MenuService } from '../menu/menu.service';
import {AuthService} from "../../../auth.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  encapsulation: ViewEncapsulation.None,
  providers: [ MenuService ],
  animations: [
    trigger('showInfo', [
      state('1' , style({ transform: 'rotate(180deg)' })),
      state('0', style({ transform: 'rotate(0deg)' })),
      transition('1 => 0', animate('400ms')),
      transition('0 => 1', animate('400ms'))
    ])
  ]
})
export class HeaderComponent implements OnInit {
  public showHorizontalMenu:boolean = true;
  public showInfoContent:boolean = false;
  public settings: Settings;
  public menuItems:Array<any>;
   public profiles:any;
   public usernameconnect:any;
    textrtl:any;
  constructor(public appSettings:AppSettings, public menuService:MenuService,private authService:AuthService) {
      this.settings = this.appSettings.settings;
      if(this.authService.role !== null){
        console.log("one"+this.authService.role);
        if (this.authService.role === 'chef_agence') {
          this.menuItems = this.menuService.getProfilAdministareur();
        }else if (this.authService.role === 'agent_guichet') {
          this.menuItems = this.menuService.getProfilAgent();
        } else {
          this.menuItems = this.menuService.getProfilClient();
        }
      }else {
        console.log("two"+sessionStorage.getItem('roles'));
        if (sessionStorage.getItem('roles') === 'Demandeur') {
          this.menuItems = this.menuService.getProfilDemandeur();
        }else if (sessionStorage.getItem('roles') === 'DirectionAchat') {
          this.menuItems = this.menuService.getProfilDirectionAchat();
        } else if (sessionStorage.getItem('roles') === 'Financier') {
            this.menuItems = this.menuService.getProfilFinancier();
        } else if (sessionStorage.getItem('roles') === 'Controlleur') {
            this.menuItems = this.menuService.getProfilControlleur();
        }
        else {
            this.menuItems = this.menuService.getProfilDirectionAchat();

        }
      }


  }

  ngOnInit() {
    if(window.innerWidth <= 768)
      this.showHorizontalMenu = false;
  }


  public closeSubMenus(){
    let menu = document.querySelector("#menu0");
      if(menu){
      for (let i = 0; i < menu.children.length; i++) {
          let child = menu.children[i].children[1];
          if(child){
              if(child.classList.contains('show')){
                child.classList.remove('show');
                menu.children[i].children[0].classList.add('collapsed');
              }
          }
      }
    }
  }

  @HostListener('window:resize')
  public onWindowResize():void {
     if(window.innerWidth <= 768){
        this.showHorizontalMenu = false;
     }
      else{
        this.showHorizontalMenu = true;
      }
  }
    signOut() {
        localStorage.clear();
        sessionStorage.clear();
        // clearMenu();
        // window.location.replace('');
    }

}
