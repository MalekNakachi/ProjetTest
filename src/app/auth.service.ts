import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import * as jwt_decode from "jwt-decode";
import {environment} from '../environments/environment.prod';
import {Observable} from "rxjs";
import {EnvService} from "../env.service";
import {Router} from "@angular/router";
import {MenuService} from "./theme/components/menu/menu.service";




@Injectable({
    providedIn: 'root',
})

export class AuthService {


    isItLoggedIn: boolean = false;
    public authTokenStale: string = 'stale_auth_token';
    public authTokenNew: string = 'new_auth_token';
    public currentToken: string = "";
    public prefixUrl = 'api/user';
    urlback:string=environment.urlBackOnas
  public menuItems:Array<any>;
    role: any = null;

    constructor(private httpClient: HttpClient,private env: EnvService, private router: Router) {
    }

  private extractData(res: Response) {
    const body = res;
    return body || { };
  }

    isLoggedIn() {
        if(this.loadToken() !== '' && this.loadToken() !== null) {
            this.isItLoggedIn = true;
        }
         return this.isItLoggedIn;
        //return true;
    }



  authentifyProfile(username, password): Promise<boolean> {
    return new Promise( (resolve, reject) => {

      this.httpClient.post(`${this.urlback + '/api/login'}`, {'username': username, 'password': password, 'application': 'mm8'}, {observe: 'response', responseType: 'json'})
        .subscribe(
          (data: any) => {
            console.log(data);
            console.log( data.headers.get('Authorization'));
            this.router.navigate(['pages/dashboard']);

            localStorage.setItem('userToken', data.headers.get('Authorization'));


            this.isItLoggedIn = true;

            // this.getRole(username);


            resolve(true);
          },
          error1 => {
            reject(error1);
          }
        )
    })
  }

  getToken(username, password): Promise<any> {
    let headers = new HttpHeaders();
    headers.append('Content-Type', "application/json");
    return new Promise( (resolve,reject) => {
      this.httpClient.post(`${this.urlback}` + '/api/login', {'username': username, 'password': password, 'application': 'mm8'}, {observe: "response", responseType: 'text' as 'json', headers})
        .subscribe(
          (data:any) => {
            this.isItLoggedIn = true;
            resolve(data);
          },
          error1 => {
            reject("");
          }
        )
    })
  }

  getRole(username){
    const params = new HttpParams().set('name', username);
    this.httpClient.get(`${this.urlback + '/api/utilisateursByNme'}`, {params})
      .subscribe(
        (data: any[]) => {
          localStorage.setItem('role', data[0]);
          this.role = data[0];
          if(data[0]  === 'chef_agence'){
            this.router.navigate(['pages/dashboard']);

          }else if(data[0]  === 'agent_guichet'){
            this.router.navigate(['pages/dashboard']);
          }else{
            this.router.navigate(['pages/credits']);
          }

        });

  }


    // saveUser(user: RegisterModel) {
    //     return this.httpClient.post(this.prefixUrl + '/add', user);
    // }


    //---------------------------------------> authentication with ldap
    authentifyUser(username, password): Promise<boolean> {
        return new Promise( (resolve,reject) => {
            this.httpClient.post(this.urlback+'login', {"username":username, "password":password}, {observe: "response", responseType: "json"})
                .subscribe(
                    (data: any) => {
                        localStorage.setItem("username", username);
                        localStorage.setItem("userToken", data.headers.get('authorization'));
                        this.isItLoggedIn = true;


                        sessionStorage.setItem("datalogin",username)

                        resolve(this.isItLoggedIn);
                    },
                    error1 => {
                        reject(error1);

                    }
                )
        })
    }


    getUserProfile(login){



    }

    loadToken(): string{
       this.currentToken = sessionStorage.getItem("token");
       return this.currentToken;
    }

    clearToken() {
        setTimeout(() => {localStorage.clear()}, this.getDecodedAccessToken().exp*1000 )
    }

    getAutorization() {
        return new HttpHeaders().append("Authorization", this.loadToken());
    }


    getDecodedAccessToken(): any {
    let token = this.loadToken();
        try{
            return jwt_decode(token);
        }
        catch(Error){
            return null;
        }
    }




    getControlDepartment(userCN: string) {
        let params = new HttpParams();
        params = params.append("userCN", userCN);

        return new Promise(
            (resolve, reject) => {
                this.httpClient.get(this.urlback+this.prefixUrl + '/chefDepartment', {params: params, headers: this.getAutorization()})
                    .subscribe( data => resolve(data),
                            error1 => reject(error1)
                    )
            }
        );
    }


    //--------------------------------------> we change to ldap (repertoire)
    getUserById(id) : Promise<any>{
        return new Promise(
            (resolve, reject) => {
                this.httpClient.get(this.urlback+this.prefixUrl + '/getById',
                    {params: new HttpParams().set('id', id)})
                    .subscribe(data => {
                            resolve(data)
                        },
                        error1 => reject(error1))
            }
        );
    }

    getAllUsers(id): Promise<any> {
        return new Promise(
            ((resolve, reject) => {
                this.httpClient.get(this.urlback+this.prefixUrl +'/getAllUsers', {headers: this.getAutorization()})
                    .subscribe(
                        data => {
                            resolve(data)
                        },
                        error => {
                            reject(error)
                        }
                    )
            })
        )
    }

    getUserGroups(userCN: string) {
        let params = new HttpParams();
        params = params.append("username", userCN);
        return new Promise( (resolve,reject) => {
            this.httpClient.get(this.urlback+this.prefixUrl  + '/getUser',{params: params, headers: this.getAutorization()})
                .subscribe(
                    (data: any) => {
                        resolve(data);
                    },
                    error1 => {
                        reject(error1);
                    }
                )
        })
    }

    // getToken(user:any): Promise<any> {
    //     let headers = new HttpHeaders();
    //     headers.append('Content-Type', "application/json");
    //     return new Promise( (resolve,reject) => {
    //         this.httpClient.post(`${this.urlback}` + 'api/login', user, {observe: "response", responseType: 'text' as 'json', headers})
    //             .subscribe(
    //                 (data) => {
    //
    //                     this.isItLoggedIn = true;
    //                     resolve(data);
    //                 },
    //                 error1 => {
    //                     reject(error1);
    //                 }
    //             )
    //     })
    // }
  getRoleUser(samaccountname,token): Observable<any> {
    let params= new HttpParams().set("samaccountname",samaccountname);
    return this.httpClient.get(`${this.urlback}`+'/api/getMyAllList',{params:params,headers: new HttpHeaders().set("Authorization", token)})
  }

  getMy(token): Observable<any> {
    let params=new HttpParams();
    params= params.append("token",token)
    return this.httpClient.get(`${this.urlback}`+'/api/getMyMM',{params:params,headers: new HttpHeaders().append("Authorization",sessionStorage.getItem("token"))});

  }
    getServeurApplication(type) :Observable<any> {



        const params = new HttpParams().set('nameconnection', type)

        return  this.httpClient.get(`${this.urlback}` + 'api/connectionsbycode', {
            params:params,
            headers: new HttpHeaders().append('Authorization', sessionStorage.getItem('token'))
        })
    }

}
