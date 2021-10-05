import {ChangeDetectorRef, Component, Input, OnInit} from "@angular/core";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {Router} from "@angular/router";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {ToastrService} from "ngx-toastr";
import {DemandeService} from "../../gestion-credit/services-demande/servicecredit.service";

class HttpClient {
}

@Component({
    selector: 'activity-grid',
    template: `

        <div id="data-grid-demo">

            <dx-data-grid
                    #grid
                    columnResizingMode="widget"
                    id="activityGridContainer"
                    [dataSource]="activitys"
                    [showBorders]="true"
                    [rowAlternationEnabled]="true"
                    [allowColumnReordering]="true"
                    [allowColumnResizing]="true"
                    [columnMinWidth]="50"
                    [hoverStateEnabled]="true"
                    [remoteOperations]="true"
                    [showColumnLines]="false"
                    [showRowLines]="false"
                    (onCellPrepared)="onCellPrepared($event)"
                    columnResizingMode="widget"
                    [(selectedRowKeys)]="selectedRows"
                    (onSelectionChanged)="selectionChangedHandler()"
                    [showBorders]="true"
                (onSaved)="onValueChanged($event)"
                (onSaving)="onValueChanged($event)"
                (onEditingStart)="logEvent('EditingStart')"
                (onInitNewRow)="logEvent('InitNewRow')"
                (onRowInserting)="logEvent('RowInserting')"
                (onRowInserted)="Onsave($event)"
                (onRowUpdating)="logEvent('RowUpdating')"
                (onRowUpdated)="update($event)"
                (onRowRemoving)="logEvent('RowRemoving')"
                (onRowRemoved)="logEvent('RowRemoved')"
                (onSaving)="onValueChanged($event)"
                (onSaved)="onValueChanged($event)"
                (onEditCanceling)="logEvent('EditCanceling')"
                (onEditCanceled)="logEvent('EditCanceled')"
                (onEditorPrepared)="onEditorPrepared($event)"
                (onEditorPreparing)="onEditorPreparing($event)"
            >
                <dxo-selection
                        mode="multiple">
                </dxo-selection>
                <dxo-editing
                        mode="row"
                        [allowUpdating]="true"
                        
                       >
                </dxo-editing>
                <dxi-column dataField="listedesproduits" caption="listedesproduits" [filterOperations]="['contains']" ></dxi-column>
                <dxi-column dataField="quantiteCommande" caption="quantite_commande"  [filterOperations]="['contains']" ></dxi-column>
            
                <dxo-paging [pageSize]="50"></dxo-paging>
                <dxo-pager         [showPageSizeSelector]="true"
                        [allowedPageSizes]="[50, 100, 200,1000]"
                        [showInfo]="true">
                </dxo-pager>

                <div *dxTemplate="let data of 'cellTemplate'">
                    <div class="dx-field1">
                        <a (click)="onEditProcessDiagram(data.data)" target="_blank" rel="noopener" title="Editer Process"><i class="fa fa-pencil-square-o" style="color:darkblue;"></i></a>&nbsp;
                        <a (click)="onShowProcessDiagram(data.data,modalDiagram1)" target="_blank" rel="noopener" title="Visualiser Process"><i class="fa fa-sitemap" style="color:darkblue;"></i></a>&nbsp;
                        <a (click)="undeployeProcess(data.data,modalDiagramUndeploye,modalDiagramUndeployeMigration)" target="_blank" title="" ><i class="fa fa-trash" title="supprimer Process" style="color: darkred;"></i></a>

                        <!--                  <span  class="task-icon-action"><i class="fa fa-pencil-square-o fa-2x" title="Editer fiche" style="color: darkblue;"-->
                        <!--                                                     (click)="onEdit(data.data)" ></i></span>-->

<!--                        <span  class="task-icon-action"><i  class="fa fa-sitemap" (click)="onShowProcessDiagram(data.data,modalDiagram1)" ></i></span>-->
<!--                            <span  class="task-icon-action"><i  class="fa fa-edit" (click)="onEditProcessDiagram(data.data)"  style="margin-left: 10%;"></i></span>-->
                        </div>
                </div>

                <div *dxTemplate="let data of 'cellTemplateTotal'">
                    <p style="color: #528dc2;"><b>{{data.data.totalProcesses}}</b></p>

                </div>
                <div *dxTemplate="let data of 'cellTemplateEnCours'">
                    <p style="color: #265287;"><b>{{data.data.nbreEnCours}}</b></p>

                </div>
                <div *dxTemplate="let data of 'cellTemplateFermer'">
                    <p style="color: #872f2d;"><b>{{data.data.nbreFermer}}</b></p>

                </div>
                <div *dxTemplate="let info of 'titleHeaderTemplateTotal'">
                    <p style="color: #528dc2;"><b>{{info.column.caption}}</b></p>
                </div>
                <div *dxTemplate="let info of 'titleHeaderTemplateEnCours'">
                    <p style="color: #265287;"><b>{{info.column.caption}}</b></p>
                </div>
                <div *dxTemplate="let info of 'titleHeaderTemplateFermer'">
                    <p style="color: #872f2d;"><b>{{info.column.caption}}</b></p>
                </div>
            </dx-data-grid>
        </div>
        <ng-template #modalDiagram1 let-modal class="modal fade right"style="width: 500px;" let-d="close" style="width: 500px;"id="frameModalTop1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="">
            <div class="modal-header" style="  background: linear-gradient(to right bottom, #0058657d, #005865);  padding: 18px !important;">
                <h4 style="color:#F5F5F5; margin-left: 33%;font-size: 191%">Processus</h4>
                <button type="button" class="close" aria-label="Close" (click)="d('Cross click')">
                    <span aria-hidden="true" style="color : #a6727b" >&times;</span>
                </button>
            </div>
            <div class="modal-body" style="background-color: #f5f5f5;"  >
                <div #myCanvas id="myCanvas" class="container-fluid" style="background-color: white;" >
                    <img  id="scream" [src]="url" style="width: 100%;height: 100%;">
                </div>
            </div>
            <div class="modal-footer" style="background-color: #f5f5f5;">
                <div class="row" style="padding-top:0.9%">
                    <div class="col-md-12">
                        <div style="float: right;" >
                            <button   type="button" class="btn btn-primary btn-rounded w-200p mb-1 mr-1 butttons-style" style="float: right;font-size: 85%;background-color:#910900;"  (click)="d('Cross click')">Fermer</button>
                        </div>
                    </div>
                </div>
            </div>
        </ng-template>


        <ng-template #modalDiagramUndeploye let-modal class="modal fade right" id="frameModalTop5" let-d="close" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" width="1000" height="1000">
            <div class="modal-header" style="  background: linear-gradient(to right bottom, #0058657d, #005865);  padding: 18px !important;">
                <h4 style="color:#F5F5F5; margin-left: 33%;font-size: 191%">Undeploye Process</h4>
                <button type="button" class="close" aria-label="Close" (click)="d('Cross click')">
                    <span aria-hidden="true" style="color : #a6727b" >&times;</span>
                </button>
            </div>
            <div class="modal-body" style="background-color: #f5f5f5;"  >
                <p>Nous avons verifier que cette version de processus n'est pas active, voulez vous supprimer</p>
            </div>
            <div class="modal-footer" style="color: #f5f5f5;">
                <div class="row" style="padding-top:0.9%">
                    <div class="col-md-12">
                        <div style="float: right;" >
                            <button   type="button" class="btn btn-primary btn-rounded w-200p mb-1 mr-1 butttons-style" style="float: right;font-size: 85%;background-color:#005865;"  (click)="delete(processInstanceID)">Confirmer</button>
                            <button   type="button" class="btn btn-primary btn-rounded w-200p mb-1 mr-1 butttons-style" style="float: right;font-size: 85%;background-color:#910900;"  (click)="d('Cross click')">Annuler</button>

                        </div>
                    </div>
                </div>
            </div>
        </ng-template>



    `,
    styleUrls: [/* './task-grid.component.scss' */]
})

export class GridActivityComponent implements OnInit {

    url:SafeResourceUrl;
    @Input() key: any;
    processInstanceID: any;
    activitys: any[] = [];
    jsonoffre:any={
        quantiteCommande:null
    }
    constructor( private toster: ToastrService, private demandeAchatSevice: DemandeService,  private router: Router,private modal: NgbModal,private changeDetectorRef: ChangeDetectorRef,public _DomSanitizationService: DomSanitizer,private toastr: ToastrService) {


    }

    ngOnInit() {
        this.getAlldemande();

    }


    ngAfterViewInit() {

    }

    selectedChanged(e: any, data: any) {

        console.log(' console',data)

    }

    onCellPrepared(e) {
        if (e.rowType === 'data') {
            if (e.data.activityName === 'Annotation') {
                if (e.column.dataField === 'activityName') {
                    e.cellElement.style.color = 'rgb(255, 0, 0)';
                }
            }
        }
    }


    update(e){
        console.log("quantité",e.data)
        this.jsonoffre=e.data
        console.log("json",this.jsonoffre )
       if((parseInt(this.jsonoffre.quantiteCommande))!=NaN){
            this.jsonoffre.quantiteCommande=parseInt(this.jsonoffre.quantiteCommande)
        }
        this.demandeAchatSevice.depotdemande(this.jsonoffre).then(data=>{
            this.toster.success("BonCommande")
        })

    }

    onShowDetails(task) {

    }

    // onShowProcessDiagram(data,modalDiagram){
    //     console.log("PROCESS INSTANCE ID :: SHOW DIAGRAM ",data.id)
    //
    //     this.serviceProcessus.getDiagramImage(data.id).subscribe((image: any)=>{
    //         let reader = new FileReader();
    //         reader.addEventListener("load", () => {
    //             this.url = reader.result;
    //         }, false);
    //
    //         if (image) {
    //             reader.readAsDataURL(image);
    //         }
    //         this.modal.open(modalDiagram,{size: 'lg'});
    //     })
    // }
    //
    //

    getAlldemande(){
        this.demandeAchatSevice.getMesDemandesListAll().subscribe((data: any[]) => {
            console.log('LIST DES PRODUITS : ', data);
            this.activitys = data;

        });
    }
}





