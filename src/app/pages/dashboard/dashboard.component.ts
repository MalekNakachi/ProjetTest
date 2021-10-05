import { Component, OnInit } from '@angular/core';
import {interval} from 'rxjs/internal/observable/interval';
import {startWith, switchMap} from 'rxjs/operators';
import {Observable} from 'rxjs/Observable';
import {FlowableService} from "../gestion-credit/services-demande/flowable.service";

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit {


    listProcessus: any[] = [];
    selectedPocessus: any;
    generalInfo: any[] = []

    public processes: any[];
    public processesTraite: any[];
    public processesEnCours: any[];

    public colorScheme1 = {
        domain: ['#606060']
    };
    public colorScheme2 = {
        domain: ['#875A7B']
    };
    public colorScheme3 = {
        domain: ['#00786A']
    };
    public colorScheme4 = {
        domain: ['#0096A6']
    };



    datasource: ComplaintsWithPercent[];
    selectedItem : any = 'Par Annee';
    chartTitle:any;


    discprocesses:any=[];



    constructor(private flowableService : FlowableService) { }

    ngOnInit() {
        this.listProcessus = []

        this.getGeneralInfo();

    }


    getGeneralInfo(){
        this.flowableService.getGeneralInfo().subscribe(Info =>{
            if(Info[0]){
                console.log("GENERAL INFO  :: " , Info );
                for (let processus of Info) {
                    this.listProcessus.push(processus['Type']);
                }
                this.generalInfo = Info
                this.chartTitle = Info[0]['Type']+' '+ this.selectedItem;
                this.processes = [{name : "Processus",value : Info[0]['nbrInstances']}];
                this.processesEnCours = [{name : "Processus En Cours",value : Info[0]['nbrNotFinishedInstances']}];
                this.processesTraite = [{name : "Processus Traité",value : Info[0]['nbrFinishedInstances']}];

                this.datasource=this.getComplaintsData(Info[0]['listProcessDemandeInstancesByMois'])

                this.discprocesses = [
                    {
                        "name": "Validé",
                        "value": Info[0]['listProcessDemandeInstancesValide']
                    },
                    {
                        "name": "Annulé",
                        "value": Info[0]['listProcessDemandeInstancesAnnule']
                    }
                ];
                // this.selectedPocessus=Info[0]['Type'];
            }
        });
    }


    getData(){
        console.log('loading')
        let loading = localStorage.getItem('loading');
        if(loading === 'true') {
            this.listProcessus = []
            this.flowableService.getGeneralInfo().subscribe(Info => {
                if(Info[0]) {

                    console.log("GENERAL INFO  :: ", Info);
                    for (let processus of Info) {
                        this.listProcessus.push(processus['Type']);
                    }
                    this.generalInfo = Info
                    this.chartTitle = Info[0]['Type'] + ' ' + this.selectedItem;
                    this.processes = [{name: "Processus", value: Info[0]['nbrInstances']}];
                    this.processesEnCours = [{name: "Processus En Cours", value: Info[0]['nbrNotFinishedInstances']}];
                    this.processesTraite = [{name: "Processus Traité", value: Info[0]['nbrFinishedInstances']}];

                    this.datasource = this.getComplaintsData(Info[0]['listProcessDemandeInstancesByMois'])

                    this.discprocesses = [
                        {
                            "name": "Validé",
                            "value": Info[0]['listProcessDemandeInstancesValide']
                        },
                        {
                            "name": "Annulé",
                            "value": Info[0]['listProcessDemandeInstancesAnnule']
                        }
                    ];
                    // this.selectedPocessus=Info[0]['Type'];
                }
                localStorage.setItem('loading','false')
            });
        }
        const data$ = new Observable(observer => {

            observer.next(1);
            observer.next(2);
            observer.next(3);
            observer.complete();

        });
        return data$;
    }
    public onSelect(event) {
        console.log(event);
    }

    selectedChangedSelectBox(e){
        console.log(this.selectedItem)
        for(let t of this.generalInfo){
            if(this.selectedItem === "Par Annee") {
                this.datasource = this.getComplaintsData(t['listProcessDemandeInstancesByMois'])
                this.chartTitle = 'Demandes '+ this.selectedItem;
            }else{
                this.datasource = this.getComplaintsData(t['listProcessDemandeInstancesByJour'])
                this.chartTitle = 'Demandes '+ this.selectedItem;
            }
        }
    }

    selectedChangedSelectBoxProcessus(e) {
        for(let t of this.generalInfo) {
            if(t['Type'] === this.selectedPocessus){
                this.chartTitle = t['Type']+' '+ this.selectedItem;
                this.processes = [{name : "Processus",value : t['nbrInstances']}];
                this.processesEnCours = [{name : "Processus En Cours",value : t['nbrNotFinishedInstances']}];
                this.processesTraite = [{name : "Processus Traité",value : t['nbrFinishedInstances']}];

                this.datasource=this.getComplaintsData(t['listProcessDemandeInstancesByMois'])

                this.discprocesses = [
                    {
                        "name": "Validé",
                        "value": t['listProcessDemandeInstancesValide']
                    },
                    {
                        "name": "Annulé",
                        "value": t['listProcessDemandeInstancesAnnule']
                    }
                ];

            }
        }
    }

    customizeTooltip = (info: any) => {
        return {
            html: "<div><div class='tooltip-header'>" +
            info.argumentText + "</div>" +
            "<div class='tooltip-body'><div class='series-name'>" +
            info.points[0].seriesName +
            ": </div><div class='value-text'>" +
            info.points[0].valueText +
            "</div><div class='series-name'>" +
            info.points[1].seriesName +
            ": </div><div class='value-text'>" +
            info.points[1].valueText +
            "% </div></div></div>"
        };
    }

    customizeLabelText = (info: any) => {
        return info.valueText + "%";
    }



    getComplaintsData(complaintsData): ComplaintsWithPercent[] {

        var data = complaintsData.sort(function (a, b) {
                return b.count - a.count;
            }),
            totalCount = data.reduce(function (prevValue, item) {
                return prevValue + item.count;
            }, 0),
            cumulativeCount = 0;
        return data.map(function (item, index) {
            cumulativeCount = item.count;
            return {
                Actif: item.Actif,
                count: item.count,
                cumulativePercent: Math.round(cumulativeCount * 100 / totalCount)
            };
        });
    }









}
export class ComplaintsWithPercent {
    Actif: string;
    count: number;
    cumulativePercent: number;
}

class Actif {
    complaint: string;
    count: number
}
