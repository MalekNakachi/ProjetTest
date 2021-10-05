import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {AuthService} from "../auth.service";
import {environment} from "../../environments/environment.prod";
import {Observable} from "rxjs";
import {EnvService} from "../../env.service";

@Injectable({
  providedIn: 'root'
})
export class PjService {


  url: string = environment.urlBackOnas + '/';


  constructor(private httpClient: HttpClient, private authService: AuthService,private env: EnvService) {
    this.url=this.env.apiUrlbtl;
  }


  addPiecejointes(piece) {
    return this.httpClient.post(`${this.url}` + 'api/pjs', piece, {
      headers: new HttpHeaders().set('Authorization', sessionStorage.getItem('token'))
    });








  }

  addPiecejointesliste(piece): Observable<Object>  {
    console.log("serviceeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee ",piece)
    return this.httpClient.post(`${this.url}` + 'api/pjsliste', piece, {
      headers: new HttpHeaders().set('Authorization', sessionStorage.getItem('token'))
    });






  }

  uploadfilealfresco(file,relativePath){
    const formData =  new FormData() ;
    formData.append('filee', file[0])
    formData.append("relativePath",relativePath) ;

    return this.httpClient.post(`${this.url}`+'api/UploadFileInAlfresco',formData,{
      headers: new HttpHeaders().append('Authorization', sessionStorage.getItem('token'))
    })

  }


  getListPjInBD(dossier): Observable<Object> {
    let params = new HttpParams();
    params = params.append("dossier", dossier);
    return this.httpClient.get(`${this.url}` + 'api/pjs-bydossier' , { params,
      headers: new HttpHeaders().set("Authorization", sessionStorage.getItem("token"))
    });


  }




  getListPj_InBD_bycompte(compte): Observable<Object> {
    let params = new HttpParams();
    params = params.append("compte", compte);
    return this.httpClient.get(`${this.url}` + 'api/pjs-bycompte' , { params,
      headers: new HttpHeaders().set("Authorization", sessionStorage.getItem("token"))
    });


  }



  updatePiecejointe(piece){
    return this.httpClient.put(`${this.url}` + 'api/pjs', piece, {headers: new HttpHeaders().set('Authorization', sessionStorage.getItem('token'))});

  }

//  alfreco path exsite***************************


  getPathExist(relativepath){
    console.log("relative path",relativepath)
    return this.httpClient.get(`${this.url}`+"api/GetExistPath?relativepath="+relativepath, {

      headers: new HttpHeaders().set("Authorization", sessionStorage.getItem("token"))})
  }

  /***************************download fileCMIS**********************************/
  downloadfileCMIS(params): Observable<any> {
    return this.httpClient.get(`${this.url}` + 'api/DownloadFile', {
      headers: new HttpHeaders().set('Authorization', sessionStorage.getItem('token')),
      observe: 'response',
      params: params,
      responseType: 'blob' as 'json'
    });
  }
  /******************************l************************************************/

}
