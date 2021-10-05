import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class EnvService {
  public apiUrlbtl ='';
  public apiUrlprofile = '';
  public apiUrlbasesysteme='';
  public urlproxysoap='';
  public nodeRefToolkit='';
  public urlorganigramme='';
  public enableDebug = true;

  constructor() {

    //this.apiUrl="resul of webservice" ;
  }




//   getServeurApplication(nameconnection) :Observable<any> {
//
//
// // const  httplient=new HttpClient(this.handler);
//     const params = new HttpParams().set('nameconnection', nameconnection)
//
//     return  this.http.get('http://localhost:8098/' + 'api/connectionsbyname', {
//       params:params,
//       headers: new HttpHeaders().append('Authorization', sessionStorage.getItem('token'))
//     })
//   }
}
