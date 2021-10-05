import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';




import {Router} from '@angular/router';

import {environment} from '../../../environments/environment';


@Injectable({
    providedIn: 'root'
})
export class GestionArticlesService {

    urlpmm: string = environment.urlBackOnas


    constructor(private httpClient: HttpClient, private router :Router) {}



    getAllarticles() {

      let headers = new HttpHeaders();
      headers.append('Content-Type', "application/json");
      return this.httpClient.get(`${this.urlpmm}` + "/api/articles");
    }

    getDemandesbyId(id) {
      let headers = new HttpHeaders();
      headers.append('Content-Type', "application/json");
      return this.httpClient.get(`${this.urlpmm}` + "/api/articles/"+id);
    }

    createArticle(article):Promise < any > {

      console.log("in get token");
      let headers = new HttpHeaders();
      headers.append('Content-Type', "application/json");
      return new Promise((resolve, reject) => {
        this.httpClient.post(`${this.urlpmm}` + '/api/articles', article, {
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
