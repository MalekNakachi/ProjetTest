import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";



@Injectable({
  providedIn: 'root'
})

export class FlowableService {

  urlmm8: string = environment.urlBackOnas + '/api/workflow';

  // public prefixUrl = 'http://192.168.10.29:8093/api/workflow';

  constructor(private httpClient: HttpClient) {

  }

  private extractData(res: Response) {
    let body = res;
    return body || {};
  }

  //start process with initiator
  startProcessInstance(name, process): Observable<any> {
    console.log(this.urlmm8)
    return this.httpClient.post(`${this.urlmm8}` + '/startProcessInstance', {'name': name, 'process': process}, {observe: 'body', headers: new HttpHeaders()})
        .pipe(map(this.extractData));
  }

  //historic of activity
  public historicTask(taskId): Observable<any> {
    return this.httpClient.post(`${this.urlmm8}` + '/historic', taskId, {observe: 'body', headers: new HttpHeaders()})
        .pipe(map(this.extractData))
  }

  // historic of process instance
  public historicProcessInstance(processInstanceId): Observable<any> {
    return this.httpClient.post(`${this.urlmm8}` + '/getHistoricProcessInstance', processInstanceId, {observe: 'body', headers: new HttpHeaders()})
        .pipe(map(this.extractData))
  }


  public getGatewayDecisionWithoutTaskId(processId): Observable<any> {
    console.log("processId : ",processId)
    let formData = new FormData();
    formData.append('processId', processId)
    return this.httpClient.post(`${this.urlmm8}` + '/getGatewayDecisionWithoutTaskId', formData, {observe: 'body', headers: new HttpHeaders()})
        .pipe(map(this.extractData))
  }


  // all decisions
  public getGatewayDecision(taskId): Observable<any> {
    console.log("taskId",taskId)
    let formData = new FormData();
    formData.append('taskid', taskId)
    return this.httpClient.post(`${this.urlmm8}` + '/gatewayDecision', formData, {observe: 'body', headers: new HttpHeaders()})
        .pipe(map(this.extractData))
  }

  // expired date of activity
  public getDelaiTask(taskId): Observable<any> {
    return this.httpClient.post(`${this.urlmm8}` + '/getdelaiTask', taskId, {observe: 'body', headers: new HttpHeaders()})
        .pipe(map(this.extractData))
  }


  getGeneralInfo(): Observable<any> {
    return this.httpClient.get(`${this.urlmm8}` + '/getInfo', {observe: 'body'})
      .pipe(map(this.extractData))
  }

  // translate to the next activity and return the current activity information
  nextTask(processInstanceId, taskId, decision, description, authentifier,params): Observable<any> {
    console.log("Translate To Next Task ------> taskid", taskId, "decision", decision)
    let formData = new FormData();
    formData.append('processInstanceId', processInstanceId)
    formData.append('taskid', taskId)
    formData.append('decision', decision)
    formData.append('description', description)
    formData.append('authentifier', authentifier)
    formData.append('listparams', params)

    return this.httpClient.post(`${this.urlmm8}` + '/nextTask', formData, {observe: 'body', headers: new HttpHeaders()})
        .pipe(map(this.extractData))
  }

//setCondidates
  setCondidates(taskid, authors, readers): Observable<any> {
    return this.httpClient.post(`${this.urlmm8}` + '/setCandidates', {
      'taskId': taskid,
      'authors': authors,
      'readers': readers
    }, {observe: 'body', headers: new HttpHeaders()})
        .pipe(map(this.extractData));
  }



    getDiagramImage(processInstanceId) {
        const httpOption = {
            responseType : 'blob' as 'blob',
          headers: new HttpHeaders()
        }
        return this.httpClient.get(`${this.urlmm8}` + '/runtime/process-instances/' + processInstanceId + '/diagram', httpOption)
    }

}
