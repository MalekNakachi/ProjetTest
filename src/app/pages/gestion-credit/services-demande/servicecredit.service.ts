import { Injectable } from '@angular/core';
import {map} from "rxjs/operators";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs/index";
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class DemandeService {

  urlOnas: string = environment.urlBackOnas + '/api';

  constructor(private httpClient: HttpClient) { }



  private extractData(res: Response) {
    let body = res;
    return body || {};
  }

  getAllarticles() {

    let headers = new HttpHeaders();
    headers.append('Content-Type', "application/json");
    return this.httpClient.get(`${environment.urlBackOnas}` + "/api/articles");
  }

    getAllOffres() {

        let headers = new HttpHeaders();
        headers.append('Content-Type', "application/json");
        return this.httpClient.get(`${environment.urlBackOnas}` + "/api/offres");}

    getAllDemandearticles(DemandeId){
        let headers = new HttpHeaders();
        headers.append('Content-Type', "application/json");
        let params = new HttpParams().set('demandeId',DemandeId);
        return this.httpClient.get(`${environment.urlBackOnas}` + "/api/getAllDemandearticles",{params:params, headers: new HttpHeaders().append("Authorization",sessionStorage.getItem("token"))});

    }

    getAllDemandeoffres(DemandeId){
        let headers = new HttpHeaders();
        headers.append('Content-Type', "application/json");
        let params = new HttpParams().set('demandeId',DemandeId);
        return this.httpClient.get(`${environment.urlBackOnas}` + "/api/getAllDemandeoffres",{params:params, headers: new HttpHeaders().append("Authorization",sessionStorage.getItem("token"))});

    }
  getAllDemandeurs() {

    let headers = new HttpHeaders();
    headers.append('Content-Type', "application/json");
    let params = new HttpParams().set('role','Demandeur');
    return this.httpClient.get(`${environment.urlBackOnas}` + "/api/utilisateursByRole",{params:params, headers: new HttpHeaders().append("Authorization",sessionStorage.getItem("token"))});
  }
  getProfilrMembers(profile) {

    let headers = new HttpHeaders();
    headers.append('Content-Type', "application/json");
    let params = new HttpParams().set('role',profile);
    return this.httpClient.get(`${environment.urlBackOnas}` + "/api/utilisateursByRole",{params:params, headers: new HttpHeaders().append("Authorization",sessionStorage.getItem("token"))});
  }


  getAllDepartements() {

    let headers = new HttpHeaders();
    headers.append('Content-Type', "application/json");
    return this.httpClient.get(`${environment.urlBackOnas}` + "/api/getAlldepartements",{ headers: new HttpHeaders().append("Authorization",sessionStorage.getItem("token"))});
  }



  getAllDemandes(): Observable<any> {

    return this.httpClient.get(`${this.urlOnas}` + '/demandes', {observe: 'body', headers: new HttpHeaders()})
      .pipe(map(this.extractData))
  }

  getMesDemandesList(authentifie, roles, profileUser): Observable<any> {
    let params=new HttpParams().set('profil',roles).set('username', authentifie).set('profileUser', profileUser);
    return this.httpClient.get(`${this.urlOnas + '/demande-achats-check-acl'}`,{params:params, headers: new HttpHeaders().append("Authorization",sessionStorage.getItem("token"))});
  }

  getMesDemandesListAll(): Observable<any> {
    let params=new HttpParams()
    return this.httpClient.get(`${this.urlOnas + '/demande-achats'}`,{params:params, headers: new HttpHeaders().append("Authorization",sessionStorage.getItem("token"))});
  }

  depotdemande(jsondepot):Promise < any > {

    console.log("in get token");
    let headers = new HttpHeaders();
    headers.append('Content-Type', "application/json");
    return new Promise((resolve, reject) => {
      this.httpClient.post(`${this.urlOnas + '/demande-achats'}`, jsondepot, {
        observe: "response",
        responseType: 'text' as 'json',
        headers
      })
        .subscribe(
          (data: any) => {
            console.log("data ", data);
            resolve(data);
          },
          error1 => {
            console.log("error:::>", error1)
            reject("s");
          }
        )
    })
  }
  getDemandesbyId(id): Observable<any> {
      let params=new HttpParams();
    return this.httpClient.get(`${this.urlOnas + '/demande-achats/'+id}`,{});
  }

  getAdministrators(): Observable<any> {
    return this.httpClient.get(`${this.urlOnas + '/getAdministrators'}`,{});
  }
  getAllUsers(): Observable<any> {
    return this.httpClient.get(`${this.urlOnas + '/utilisateurs'}`,{});
  }


  saveDemande(demande) {
    return this.httpClient.post(`${this.urlOnas + '/demande-achats'}`, demande,{ headers: new HttpHeaders().append("Authorization",sessionStorage.getItem("token"))});
  }

    createOffre(offre):Promise < any > {

        console.log("in get token");
        let headers = new HttpHeaders();
        headers.append('Content-Type', "application/json");
        return new Promise((resolve, reject) => {
            this.httpClient.post(`${environment.urlBackOnas}` + '/api/offres', offre, {
                observe: "response",
                responseType: 'text' as 'json',
                headers
            })
                .subscribe(
                    (data: any) => {
                        console.log("Response Create Offre ", data);
                        resolve(data);
                    },
                    error1 => {
                        console.log("error create Offre:::>", error1)
                        reject("s");
                    }
                )
        })
    }
    saveArticle(article):Promise < any > {

        console.log("in get token");
        let headers = new HttpHeaders();
        headers.append('Content-Type', "application/json");
        return new Promise((resolve, reject) => {
            this.httpClient.post(`${this.urlOnas}` + '/articles', article, {
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

    // public createOffre(offre):void {
    //     console.log("values", offre);
    //     offre['listeDesProduits'] = this.ArrayToString(this.selectedArticles);
    //     offre['nomFournisseur'] = this.ArrayToString(this.selectedFournisseurs)
    //
    //     this.DemandeService.createOffre(offre)
    //         .then(
    //             (Response:any) => {
    //                 console.log(Response);
    //                 this.router.navigate(['/pages/offres']);}  ,
    //
    //             err => {
    //                 // this.toster.error("Erreur")
    //             })
    //
    //
    // }



    public saveAcl(etat, idDossier, auteur, lecteur): Promise<any> {

    let params=new HttpParams().set("id", idDossier).set("authors", auteur).set("readers", lecteur).set("etat", etat)

    return new Promise(
      ((resolve, reject) => {
        this.httpClient.get(`${this.urlOnas + '/demande-achats-acl'}`,{params:params, headers: new HttpHeaders().append("Authorization",sessionStorage.getItem("token"))})
          .subscribe((data: any) => {
              resolve(data)
            },
            error => {
              reject(error)
            });
      })
    );
  }

  public updateAcl(idDossier, auteur, lecteur, status): Promise<any> {

    let formdata=new FormData()
    formdata.append("id", idDossier)
    formdata.append("auteur", auteur)
    formdata.append("lecteur", lecteur)
    formdata.append("status", status)

    return new Promise(
      ((resolve, reject) => {
        this.httpClient.post(`${environment.urlBackOnas + '/acl/api/update_acl'}`,formdata)
          .subscribe((data: any) => {
              resolve(data)
            },
            error => {
              reject(error)
            });
      })
    );
  }

  getDemandeAccess(authentifie, idDemande){
    let params=new HttpParams().set("authentifie",authentifie).set("id",idDemande);
    return this.httpClient.get(`${environment.urlBackOnas + '/acl/api/getAccess'}`, {params});

  }












  getReference(id) {
    let headers = new HttpHeaders();
    headers.append('Content-Type', "application/json");
    return this.httpClient.get(`${environment.urlBackOnas}` + "/api/reference/reference/"+id);
  }

}
