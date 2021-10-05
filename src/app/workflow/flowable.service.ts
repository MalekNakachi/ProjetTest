import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {environment} from "../../environments/environment.prod";
import {EnvService} from "../../env.service";



@Injectable({
  providedIn: 'root'
})

export class FlowableService {

  urlBackBtl: string = environment.urlBackOnas + '/' + 'api/workflow';

  // public prefixUrl = 'http://192.168.10.29:8093/api/workflow';

  constructor(private httpClient: HttpClient,private env: EnvService) {
this.urlBackBtl=this.env.apiUrlbtl +'api/workflow';
  }

  private extractData(res: Response) {
    let body = res;
    return body || {};
  }

  //start process with initiator
  startProcessInstance(name, process): Observable<any> {
    return this.httpClient.post(`${this.urlBackBtl}` + '/startProcessInstance', {'name': name, 'process': process}, {observe: 'body', headers: new HttpHeaders().set("Authorization", sessionStorage.getItem("token"))})
        .pipe(map(this.extractData));
  }

  //historic of activity
  public historicTask(taskId): Observable<any> {
    return this.httpClient.post(`${this.urlBackBtl}` + '/historic', taskId, {observe: 'body', headers: new HttpHeaders().set("Authorization", sessionStorage.getItem("token"))})
        .pipe(map(this.extractData))
  }

  // historic of process instance
  public historicProcessInstance(processInstanceId): Observable<any> {
    return this.httpClient.post(`${this.urlBackBtl}` + '/getHistoricProcessInstance', processInstanceId, {observe: 'body', headers: new HttpHeaders().set("Authorization", sessionStorage.getItem("token"))})
        .pipe(map(this.extractData))
  }


  public getGatewayDecisionWithoutTaskId(processId): Observable<any> {
    console.log("processId : ",processId)
    let formData = new FormData();
    formData.append('processId', processId)
    return this.httpClient.post(`${this.urlBackBtl}` + '/getGatewayDecisionWithoutTaskId', formData, {observe: 'body', headers: new HttpHeaders().set("Authorization", sessionStorage.getItem("token"))})
        .pipe(map(this.extractData))
  }


  // all decisions
  public getGatewayDecision(taskId): Observable<any> {
    console.log("taskId",taskId)
    let formData = new FormData();
    formData.append('taskid', taskId)
    return this.httpClient.post(`${this.urlBackBtl}` + '/gatewayDecision', formData, {observe: 'body', headers: new HttpHeaders().set("Authorization", sessionStorage.getItem("token"))})
        .pipe(map(this.extractData))
  }

  // expired date of activity
  public getDelaiTask(taskId): Observable<any> {
    return this.httpClient.post(`${this.urlBackBtl}` + '/getdelaiTask', taskId, {observe: 'body', headers: new HttpHeaders().set("Authorization", sessionStorage.getItem("token"))})
        .pipe(map(this.extractData))
  }


  // translate to the next activity and return the current activity information
  nextTask(processInstanceId, taskId, decision, description,authentifier,params): Observable<any> {
    console.log("Translate To Next Task ------> taskid", taskId, "decision", decision,"authentifier",authentifier)
    let formData = new FormData();
    formData.append('processInstanceId', processInstanceId)
    formData.append('taskid', taskId)
    formData.append('decision', decision)
    formData.append('description', description)
    formData.append('authentifie', authentifier)
    formData.append('listparams', params)

    return this.httpClient.post(`${this.urlBackBtl}` + '/nextTask', formData, {observe: 'body', headers: new HttpHeaders().set("Authorization", sessionStorage.getItem("token"))})
        .pipe(map(this.extractData))
  }

//setCondidates
  setCondidates(taskid, authors, readers): Observable<any> {
    return this.httpClient.post(`${this.urlBackBtl}` + '/setCandidates', {
      'taskid': taskid,
      'authors': authors,
      'readers': readers
    }, {observe: 'body', headers: new HttpHeaders().set("Authorization", sessionStorage.getItem("token"))})
        .pipe(map(this.extractData));
  }
}
