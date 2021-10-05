import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GestionRolesService {
  private baseUrl = environment.urlBackOnas + '/';


  constructor(private http: HttpClient) {
  }


  public get_all_users(): Observable<any> {
    return this.http.get(`${this.baseUrl}` + 'api/utilisateurs', {headers: new HttpHeaders().append('Authorization', localStorage.getItem('userToken'))});
  }

  public get_all_roles(): Observable<any> {
    return this.http.get(`${this.baseUrl}` + 'api/roles', {headers: new HttpHeaders().append('Authorization', localStorage.getItem('userToken'))});
  }


  public create_role(role): Observable<any> {
    return this.http.post(`${this.baseUrl}` + 'api/roles', role, {headers: new HttpHeaders().append('Authorization', localStorage.getItem('userToken'))});
  }

  public update_role(role): Observable<any> {
    return this.http.put(`${this.baseUrl}` + 'api/roles', role, {headers: new HttpHeaders().append('Authorization', localStorage.getItem('userToken'))});
  }

  public delete_role(role): Observable<any> {
    return this.http.delete(`${this.baseUrl}` + 'api/roles/' + role.id, {headers: new HttpHeaders().append('Authorization', localStorage.getItem('userToken'))});
  }


}
