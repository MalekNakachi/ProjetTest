import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "./auth.service";
import {Injectable} from "@angular/core";

@Injectable({providedIn: 'root'})
export class AuthGuardService  implements CanActivate{
    currentToken: string;
    menu:string;
    constructor(private authService: AuthService, private router: Router) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    //     if(!this.authService.isLoggedIn()) {
    //         this.router.navigate(['login']);
    //     }
    //
    //     return this.authService.isLoggedIn();
    // }
        this.currentToken = sessionStorage.getItem("token");

        if(this.currentToken == '' || this.currentToken == null) {
            this.menu=localStorage.getItem("profiles")
            this.router.navigate(['']);
        }
        return true;
    }
}
