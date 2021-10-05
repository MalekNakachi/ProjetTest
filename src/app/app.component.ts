import { Component, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { AppSettings } from './app.settings';
import { Settings } from './app.settings.model';
import {AlfrescoService} from './fileModule/alfresco_services/AlfrescoApi.service';
import {EnvService} from "../env.service";
import {AuthService} from "./auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent {
    public settings: Settings;
    textrtl:any;
    constructor(public appSettings:AppSettings, private router:Router,private auth:AuthService, private serverService: AlfrescoService,private env: EnvService){
        let language=sessionStorage.getItem("langue");
        if(language==="ar"){
            this.textrtl=true;
        }else{
            this.textrtl=false;
        }
        // if(!this.serverService.isLoggedIn()) {
            console.log(this.serverService.isLoggedIn());
            // this.serverService.loginWithUsernameAndPassword('admin', 'admin').then(
            //     data => console.log(data),
            //     error => console.log(error)
            // )

            // this.serverService.getServeurApplication("Alfresco").subscribe((data:any)=>
            //     {
            //         console.log("data alfresco===>" ,data) ;
            //         sessionStorage.setItem("host_alfresco", data.host);
            //         sessionStorage.setItem("user_alfresco", data.user);
            //         sessionStorage.setItem("password_alfresco", data.password);
            //
            //
            //     }
            // )
        // }
        this.settings = this.appSettings.settings;

        if(env.enableDebug) {
            console.log('Debug mode enabled!');
        }




        // this.auth.getServeurApplication("ServeurApplication").subscribe((dataofUrl:any)=>
        // {
        //     console.log("dataofUrl ",dataofUrl) ;
        //     this.env.apiUrl=dataofUrl;
        // })




    }


    /* These following methods used for theme preview, you can remove this methods */
    
    // ngOnInit() { 
    //     var demo = this.getParameterByName('demo');
    //     this.setLayout(demo);
    // }
    
    // private getParameterByName(name) {
    //     var url = window.location.href;
    //     name = name.replace(/[\[\]]/g, "\\$&");
    //     var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
    //         results = regex.exec(url);
    //     if (!results) return null;
    //     if (!results[2]) return '';
    //     return decodeURIComponent(results[2].replace(/\+/g, " "));
    // }

    // private setLayout(demo){
    //      switch (demo) {
    //         case "vertical-default":
    //             this.settings.theme.menu = 'vertical';
    //             this.settings.theme.menuType = 'default';
    //             break;
    //         case "vertical-compact":
    //             this.settings.theme.menu = 'vertical';
    //             this.settings.theme.menuType = 'compact';
    //             break;
    //         case "vertical-mini":
    //             this.settings.theme.menu = 'vertical';
    //             this.settings.theme.menuType = 'mini';
    //             break;
    //         case "horizontal-default":
    //             this.settings.theme.menu = 'horizontal';
    //             this.settings.theme.menuType = 'default';
    //             break;
    //         case "horizontal-compact":
    //             this.settings.theme.menu = 'horizontal';
    //             this.settings.theme.menuType = 'compact';
    //             break;
    //         case "horizontal-mini":
    //             this.settings.theme.menu = 'horizontal';
    //             this.settings.theme.menuType = 'mini';
    //             break;
    //         default:
    //             this.settings.theme.menu = 'vertical';
    //             this.settings.theme.menuType = 'default';
    //     }
    //     this.router.navigate(['/']);
    // }
   
}
