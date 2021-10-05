import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';




import {Router} from '@angular/router';

import {environment} from '../../../environments/environment';


@Injectable({
    providedIn: 'root'
})
export class GestionBoncommandeService {

    urlpmm: string = environment.urlBackOnas


    constructor(private httpClient: HttpClient, private router :Router) {}



  getAllBonCommande() {

      let headers = new HttpHeaders();
      headers.append('Content-Type', "application/json");
      return this.httpClient.get(`${this.urlpmm}` + "/api/bon-commandes");
    }
  getMesDemandesList(authentifie): Observable<any> {
    let params=new HttpParams()
    return this.httpClient.get(`${this.urlpmm + '/api/demande-achats'}`,{});
  }
    getDemandesbyId(id) {
      let headers = new HttpHeaders();
      headers.append('Content-Type', "application/json");
      return this.httpClient.get(`${this.urlpmm}` + "/api/bon-commandes/"+id);
    }

  createBonCommande(fournisseurs):Promise < any > {

      console.log("in get token");
      let headers = new HttpHeaders();
      headers.append('Content-Type', "application/json");
      return new Promise((resolve, reject) => {
        this.httpClient.post(`${this.urlpmm}` + '/api/bon-commandes', fournisseurs, {
          observe: "response",
          responseType: 'text' as 'json',
          headers
        })
          .subscribe(
            (data: any) => {
              console.log("Response Create Article ", data);
              resolve(data);
            },
            error1 => {
              console.log("error create Article:::>", error1)
              reject("s");
            }
          )
      })
    }

    getReference(id) {
      let headers = new HttpHeaders();
      headers.append('Content-Type', "application/json");
      return this.httpClient.get(`${this.urlpmm}` + "/api/reference/reference/"+id);
    }

}
