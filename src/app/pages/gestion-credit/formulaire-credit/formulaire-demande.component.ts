import {ChangeDetectionStrategy, ChangeDetectorRef, Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FlowableService} from "../services-demande/flowable.service";
import {DxDataGridComponent} from "devextreme-angular/ui/data-grid";
import config from "devextreme/core/config";
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from "@angular/material-moment-adapter";
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from "@angular/material/core";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {CheckBoxSelectionService, MultiSelectComponent} from "@syncfusion/ej2-angular-dropdowns";
import {DemandeService} from "../services-demande/servicecredit.service";


config({
  floatingActionButtonConfig: {
    icon: 'rowfield',
    position: {
      my: 'right bottom',
      at: 'right bottom',
      offset: '-26 -26'
    }
  }
});

@Component({
  selector: 'app-formulaire-demande',
  templateUrl: './formulaire-demande.component.html',
  styleUrls: ['./formulaire-demande.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [DemandeService,
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},CheckBoxSelectionService],
})
export class FormulaireDemandeComponent implements OnInit {


  identifiant:any;
  reference:any;

  departements: any[] = [];
  selectedDepartements: any[] = [];

  demandeurs: any[] = [];
  selectedDemandeurs: any[] =[];

  articles: any[] = [];
    selectedArticles: any[] = [];
    selectedFournisseurs: any[] = [];

  commentaire: any = '';

  demande = {
    id: null,
    identifiant: null,
    nomDemandeur:null,
    date:null,
    tel:null,
    email:null,
    quantite:null,
    status:null,
    serviceDep:null,
    articles:null,
    processInstanceId: null,
    activityId: null,
    activityName: null,
      offres: null,
  }

  historicTasks: any[] = [];
  decisionTab: any[] = [];

  url:SafeResourceUrl;
    offres: any[] = [];

  authentifie: any;
  mode: any = false;

  constructor(private router:Router,private activatedroute:ActivatedRoute,private modal: NgbModal,private demandeService: DemandeService,  private flowableService: FlowableService,private changeDetectorRef: ChangeDetectorRef,public _DomSanitizationService: DomSanitizer) {
    this.authentifie = localStorage.getItem('username');
    if (this.activatedroute.snapshot.params['id'] != null) {
      this.demandeService.getDemandesbyId(this.activatedroute.snapshot.params['id']).subscribe((data:any)=>{
        this.demande = data;
        this.getProcessInstanceHistoric(data['processInstanceId']);
        this.getdecisions(data['activityId']);
          this.getAllDemandeurs();
          this.getAllDepartements();
          this.getAllDemandeArticles();
          this.getAllDemandeOffres();

      });


    }else{
        this.flowableService.startProcessInstance('root','process').subscribe(data=>{
            this.demande.processInstanceId = data[1];
            this.demande.activityId = data[0];
            this.demande.activityName = data[2];
            this.getProcessInstanceHistoric(data[1]);
            this.getdecisions(data[0]);
        });
        this.getAllArticles();
        this.getAllDemandeurs();
      this.getAllDepartements();
        this.getAllOffres();
    }


  }

  ngOnInit() {


  }


  getAllDepartements(){
    this.demandeService.getAllDepartements().subscribe((listDepartements:any[])=>{
      console.log("list des departemets",listDepartements)

      this.departements = listDepartements;
        this.departements.forEach(elem=> {
            if ((this.demande.serviceDep !== null) && (this.demande.serviceDep.indexOf(elem) !== -1)) {
                this.selectedDepartements.push(elem);
            }
        });
    });
  }

  getAllDemandeurs(){
    this.demandeService.getAllDemandeurs().subscribe((listDemandeurs:any[])=>{
      console.log("list des demandeurs",listDemandeurs)

      this.demandeurs = listDemandeurs;
        this.demandeurs.forEach(elem=> {
            if ((this.demande.nomDemandeur !== null) && (this.demande.nomDemandeur.indexOf(elem) !== -1)) {
                this.selectedDemandeurs.push(elem)
            }
        });
    });
  }



    getAllDemandeArticles() {
        this.demandeService.getAllDemandearticles(this.demande.id).subscribe((listarticles:any[])=>{
            console.log("list des articles",listarticles)

            this.articles = listarticles;
        });
    }

    getAllDemandeOffres() {
        this.demandeService.getAllDemandeoffres(this.demande.id).subscribe((listOffres:any[])=>{
            console.log("list des offres",listOffres)

            this.offres = listOffres;
        });
    }

    getAllOffres() {
    this.offres = []
        this.demandeService.getAllOffres().subscribe((offres:any[])=>{
            console.log("list des offres",offres)
            offres.forEach(elem=>{
              if(elem['demandeAchat'] == null){
                  this.offres.push(elem)
              }

            });
        });
    }


  getAllArticles() {
    this.demandeService.getAllarticles().subscribe((listarticles:any[])=>{
      console.log("list des articles",listarticles)

        this.articles = listarticles;
    });
  }

  selectedChanged(evt,rows){
    console.log(rows)
      console.log(evt)

      this.demande.articles = rows;
  }
    selectedChanged12(evt,rows){
        console.log(rows)
        console.log(evt)

        this.demande.offres = rows;
    }
  getdecisions(taskId) {

    this.flowableService.getGatewayDecision(taskId).subscribe(result => {
      console.log('result WS GET Decision', result);
      this.decisionTab = result;
    });
    return this.decisionTab;
  }



  getProcessInstanceHistoric(processInstanceId){
    this.flowableService.historicProcessInstance(processInstanceId)
      .subscribe(
        (data: any[]) => {
          console.log('historictask======>', data);
          data.forEach(d => {
            const histTab = d.split(' * ');

            if (histTab[1] === null) {
              histTab[1] = '---------';
            }

            const obj = {
              taskName: histTab[0],
              decision: histTab[1],
              assignee: histTab[2],
              startTime: histTab[3],
              endTime: histTab[4],
              duree: histTab[5],
              description: histTab[6]
            };
            this.historicTasks.push(obj);
          });
        }
      );
    console.log(this.historicTasks);

  }









  routerWF(processInstanceId,taskid,decision,comment,authentifier) {

    let listAuthors = [];
    let listReaders = [];
    listReaders.push(this.authentifie);
    console.log(listAuthors)
    console.log(listReaders)

      this.flowableService.nextTask(processInstanceId,taskid, decision, comment, this.authentifie,[]).subscribe(
      resultNextTask => {
        console.log("result",resultNextTask)

        if(resultNextTask[0] === "EndEvent"){
          this.demande.activityId = resultNextTask[0];
          this.demande.activityName = resultNextTask[1];
          this.saveDemande(this.demande, [], [])
        }else {
          this.demande.activityId = resultNextTask[0];
          this.demande.activityName = resultNextTask[3];
          this.demandeService.getProfilrMembers(resultNextTask[1]).subscribe((listMembers:any[])=>{
            console.log(this.demande);
            this.saveDemande(this.demande, listMembers, [])
          });
        }
        return resultNextTask
      });
  }

  saveDemande(demande,listAuthors,listReaders) {
      demande['serviceDep'] = this.ArrayToString(this.selectedDepartements);
      demande['nomDemandeur'] = this.ArrayToString(this.selectedDemandeurs)
    if(this.demande.id === null){

    this.demandeService.saveDemande(demande).subscribe(demandeResult => {
      console.log("Demande Saved Successfuly :: ",demandeResult);

      this.saveAcl('Create',demandeResult['id'], listAuthors, listReaders);
      this.router.navigate(['/pages/demandes']);
        demandeResult['offres'] = null;
        demandeResult['articles'] = null;


        for(let article of this.demande.articles){
          article.demandeAchat = demandeResult;
          this.demandeService.saveArticle(article)
              .then(
                  (Response:any) => {
                      console.log(Response);
                     }  ,

                  err => {
                      // this.toster.error("Erreur")
                  })
      }
        for(let offre of this.demande.offres){
            offre.demandeAchat = demandeResult;
            this.demandeService.createOffre(offre)
                .then(
                    (Response:any) => {
                        console.log(Response);
                        }  ,

                    err => {
                        // this.toster.error("Erreur")
                    })
        }

    });
    }else{
        this.demandeService.saveDemande(demande).subscribe(demandeResult => {
            console.log("Demande Saved Successfuly :: ",demandeResult);

            this.saveAcl('Update',demandeResult['id'], listAuthors, listReaders);
            this.router.navigate(['/pages/demandes']);
            demandeResult['offres'] = null;
            demandeResult['articles'] = null;

            for(let article of this.articles){
                article.demandeAchat = demandeResult;
                this.demandeService.saveArticle(article)
                    .then(
                        (Response:any) => {
                            console.log(Response);
                        }  ,

                        err => {
                            // this.toster.error("Erreur")
                        })
            }
            for(let offre of this.offres){
                offre.demandeAchat = demandeResult;
                this.demandeService.createOffre(offre)
                    .then(
                        (Response:any) => {
                            console.log(Response);
                        }  ,

                        err => {
                            // this.toster.error("Erreur")
                        })
            }
        });
    }
  }

  saveAcl(etat, idDemande, ListAut, ListLec) {

    this.demandeService.saveAcl(etat, idDemande, ListAut, ListLec);

  }



  openDiagramModal(modalDiagram){
    console.log("PROCESS INSTANCE ID :: SHOW DIAGRAM ")

    this.flowableService.getDiagramImage(this.demande.processInstanceId).subscribe((image: any)=>{
      let reader = new FileReader();
      reader.addEventListener("load", () => {
        this.url = reader.result;
      }, false);

      if (image) {
        reader.readAsDataURL(image);
      }
      this.modal.open(modalDiagram,{size: 'lg'});
    })
  }

  identifier(){

    this.demandeService.getReference(4).subscribe((data:any)=>{

      this.demande.identifiant=data.nomenclature+"_"+ new Date().getFullYear()+ "_"+ data.compteur;
    })
  }

  ArrayToString(List) {
    let str = '';
    for (let t of List) {
      str = str + t + ',';
    }
    return str.slice(0, str.length - 1);
  }
}
