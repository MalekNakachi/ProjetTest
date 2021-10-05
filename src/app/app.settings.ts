import { Injectable } from '@angular/core';
import { Settings } from './app.settings.model';

@Injectable()
export class AppSettings {
    public settings = new Settings(
        'StartNG',
        'Angular Admin Template with Bootstrap 4',
        {
            menu: 'horizontal', //horizontal , vertical
            menuType: 'default', //default, compact, mini
            showMenu: true,
            navbarIsFixed: false,
            footerIsFixed: false,
            sidebarIsFixed: false,
            showSideChat: false,
            sideChatIsHoverable: true,
            skin:'combined'  //light , dark, blue, green, combined, purple, orange, brown, grey, pink          
        }
    )
}