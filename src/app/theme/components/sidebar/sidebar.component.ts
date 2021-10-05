import { Component, OnInit, ViewEncapsulation, HostListener } from '@angular/core';
import { AppSettings } from '../../../app.settings';
import { Settings } from '../../../app.settings.model';
import { MenuService } from '../menu/menu.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
  encapsulation: ViewEncapsulation.None,
  providers: [ MenuService ]
})
export class SidebarComponent implements OnInit {
  public settings: Settings;
  public menuItems:Array<any>;
  private profiles:any;
  constructor(public appSettings:AppSettings, public menuService:MenuService) {
      this.settings = this.appSettings.settings;
      console.log("in sidebar")
     // this.menuItems = this.menuService.getVerticalMenuItems();

      // this.profiles=sessionStorage.getItem("profiles");
      //
      // if (this.profiles  ==='BTLAdmin') {
      //     this.menuItems = this.menuService.getProfilAdministareur()
      // } else
      //     {
      //         this.menuItems = this.menuService.getProfilAdministareur();
      //
      // }
    this.menuItems = this.menuService.getProfilAdministareur();




      // else if(this.profiles  ==='BTLAgent') {
      //     this.menuItems = this.menuService.getProfilAgent();
      // }else if(this.profiles==='BTLChefAgence'){
      //     this.menuItems = this.menuService.getProfilChefAgent()
      // }else if(this.profiles==='BTLAudit'){
      //     this.menuItems=this.menuService.getprofilAuditDG()
      // } else if(this.profiles==='BTLManager'){
      //     this.menuItems=this.menuService.getProfilManager()
      // }
      // else if(this.profiles==='PicoProfile'){
      //     this.menuItems=this.menuService.getProfilPico()
      // }
      // else if(this.profiles==='BTLAccountManager'){
      //     this.menuItems=this.menuService.getProfilAccountManager();
      // }




  }

  ngOnInit() {
    if(sessionStorage["userMenuItems"]) {
      let ids = JSON.parse(sessionStorage.getItem("userMenuItems"));
      let newArr = [];
      ids.forEach(id => {
        let newMenuItem = this.menuItems.filter(mail => mail.id == id);
        newArr.push(newMenuItem[0]);
      });
      this.menuItems = newArr;
    }
  }

  public closeSubMenus(){
    let menu = document.querySelector("#menu0");
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
