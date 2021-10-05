import {Injectable} from "@angular/core";
import {
    HttpErrorResponse,
    HttpEvent,
    HttpHandler,
    HttpInterceptor,
    HttpRequest,
    HttpResponse
} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {AuthService} from "./auth.service";
import  'rxjs/add/operator/do';
import {Router} from "@angular/router";

@Injectable()
export class MyInterceptor implements HttpInterceptor{
    copieReq;
    constructor(private auth: AuthService, private router: Router) {}
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token: string = localStorage.getItem("userToken");
        this.copieReq = req.clone();
        if (token) {
            this.copieReq = req.clone({ headers: req.headers.set('Authorization', token) });
        }
        return next.handle(this.copieReq)
            .do((event: HttpEvent<any>) => {
                if (event instanceof HttpResponse) {
                }
            }, (err: any) => {
                if (err instanceof HttpErrorResponse) {
                    if (err.status === 403) {
                        console.log('403 error');
                        this.router.navigate(['login']);
                    }
                }
            });
    }
}