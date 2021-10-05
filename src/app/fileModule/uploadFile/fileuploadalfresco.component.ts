import {
    Component,
    ViewEncapsulation,
    ChangeDetectorRef,
    ViewChild,
    TemplateRef,
    Output,
    EventEmitter,
    Input,
    ElementRef, AfterViewInit, OnInit
} from '@angular/core';
import { NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {AbstractControl, FormControl, FormGroup} from '@angular/forms';
import {ServiceFileService} from '../alfresco_services/service-file.service';

import {AuthService} from '../../auth.service';
import {AlfrescoService} from '../alfresco_services/AlfrescoApi.service';
import {ToastrService} from "ngx-toastr";
// import {AdministrationService} from "../../pages/administrateur/administration.service";
import {PjService} from "../../pages/pj.service";

@Component({
    selector: 'app-fileuploadalfresco',

    templateUrl: './fileuploadalfresco.component.html',
    styleUrls: ['./fileuploadalfresco.component.scss'],

})
export class FileuploadalfrescoComponent implements OnInit, AfterViewInit {
    loadIndicatorVisible=false
     DateObj = new Date();
    @ViewChild('namefileModel', {static: true}) namefileModel: TemplateRef<any>;
    @ViewChild('visualisation', {static: true}) visualisation: TemplateRef<any>;
    @ViewChild('fichesignalique', {static: true}) fichesignalique: TemplateRef<any>;
    @ViewChild('confirmmodal', {static: true}) confirmmodal: TemplateRef<any>;





    @Output() messageEvent = new EventEmitter<any>();
    parentNodeRef: any;
    @Input() cle;
    dataSourcePj: any[] = [];
    checkBoxValue: boolean;
    tableaufilenew: any [] = []
    @Input() iddossier: any;
    @Input() nameeee: any;
    @Input() variblecompte: any;

    PJrattacher: any;
    title:"SVP Confirmer" ;
    message1:"Load paper if you want to scan more documents" ;
    message:"Veuillez charger un papier si vous voulez scanner plus de documents" ;
    btnCancelText:"Annuler" ;
    btnOkText:"Valider" ;
    decisionscan:any=true;

    doc_viewer = {
        pdfSrcc: '',
        visionneuse: '',
    };


    @Output() docviewer = new EventEmitter<any>();
    // @Input() nameeeee: any;
    varrautre: any;
    pdfsrcc: any;
    datauserprofile: any;
    nomfileautre: any = '';
    varr: any = '';
    menu1: any;
    modalReference: NgbModalRef;
    name1: any;
    public images: string[] = [];
    tab2: any[] = [];
    arraybuffer: any;
    events: Array<string> = [];
    namefilescan: any = '';
    tableau: any [] = [];
    tabb: any [] = [];
    bufferarray: any[] = [];
    tableaufile: any[] = [];
    pieceJoinNom = ['cin', 'tt'];
    tabaux: any [] = [];
    alfres: any;
    nameF: any;
    typef: any;
    color: any;
    nodeRef: any;
    word: boolean = false;
    test: boolean = true;
    selectedRows: String[];
    note: string;
    nb: any;
    interFile: File;
    varo: any;
    var1: any;
    var2: any;
    aux: any;
    tab = new Array();
    tab1 = new Array();

    fileReader: any;
    public modalRef: NgbModalRef;


    public date: AbstractControl;

    public number: AbstractControl;
    hiddenNameFile: boolean = false;
    form = new FormControl();
    testtab: any = [];
    index: any;
    pdfsrc: any;
    testvesionning: boolean = false;
    public testscann: boolean = false;
    public testclosemodal: boolean = false;
    pieceJoinNom1: any[] = [];
    piecejointeliste: any[] = [];
    activitevisibilite: any = false;
    dossierpj: any;
    comptepj:any;

    formejuridique: any;

    datadossier: any;


    modedocument:any;
    constructor(private toster: ToastrService, private servicefile: ServiceFileService, private alfresco: AlfrescoService, private PJservice: PjService, private pjService: PjService, private alfrescoApi: AlfrescoService, private toastr: ToastrService, private changeDetectorRef: ChangeDetectorRef, private modal: NgbModal, public modalService: NgbModal, private serviceFile: ServiceFileService, private  authservice: AuthService, private alfrescofilemodule: AlfrescoService) {






        console.log("send Variable", this.nameeee);
        this.images = [];
        this.tab = [];
        this.note = '';
        console.log('inter ', this.interFile);
        // this.serviceFile.tableauFile = this.pieceJoinNom1;
        this.parentNodeRef = sessionStorage.getItem("nodeRefParent");


        // this.gelListPjFicheClient() ;


        console.log("::::::::::::::::::", this.cle);

        // this.gelListPjdossier();






    }

    ngOnInit() {
// this.modedocument=false ;

        if (this.cle === 'fromcompte') {
            console.log("in pj by compte")
            this.gelListPjFicheCompte();

        }
        if (this.cle === 'fromdossier') {
            this.gelListPjdossier();
            // this.dossierService.getAcessFicheClient(this.iddossier).subscribe(resultacces => {
            //     if (resultacces === true) {
            //         this.modedocument= true;
            //
            //     } else {
            //         this.modedocument= false;
            //
            //
            //     }
            // });
            console.log("in pj by type dossier")
        }



    //         if (this.cle === 'fromcredit') {
    //             this.administrationService.getPiecejointeByCredit("Un prêt auto").subscribe(data => {
    //                 console.log("data credit:"+data.length)
    //
    //                 if (data.length != 0) {
    //
    //
    //                     this.pieceJoinNom1 = [];
    //                     this.piecejointeliste = []
    //                     for (let i = 0; i < data.length; i++) {
    //                         // if(datadossier.fonctionPEP==="Oui")
    //                         console.log("elementi credit"+data[i].obligatoire)
    //                         this.dataSourcePj.push(
    //                             {
    //
    //                                 id: i,
    //                                 obligatoire: data[i].obligatoire,
    //                                 pieceafournir: data[i].pieceafournir,
    //                                 cocher: null,
    //                                 pjfournie: false,
    //                                 name: data[i].nompiece,
    //                                 nomfichierrattache: null,
    //                                 tableaufile: [],
    //                                 noderef: null,
    //                                 path: null,
    //                                 noderefparent: null,
    //                                 typefileratache: null,
    //                                 valider: null,
    //                                 dossierPJ: null,
    //                                 comptePJ: null,
    //                                 version: null
    //                             }
    //                         )
    //
    //                     }
    //                 }
    //                 console.log("in pj by type credit"+this.pieceJoinNom1.length)
    //             })
    //
    //
    //
    //
    //         }
    //
    //
    //
     }



    pathExistOrNot(path) :any{

        return new Promise(
            (resolve, reject) => {
                let retour
                this.pjService.getPathExist(path).subscribe(dataexist => {

                    if (dataexist == false) {
                        console.log("path not exist==> createFolder");

                        retour=false
                    }
                    else {


                        retour=true ;
                        console.log("FileEXISTE");

                    }
                    resolve(retour)


                })

            }

        )}




    ngAfterViewInit() {

    }


    initdoc_viewer() {
        let json = {
            noderef: null,
            typefileratache: null
        }
        for (let t of this.dataSourcePj) {
            console.log("in pdf viewer::::")
            if ((t.noderef != null)) {
                json.noderef = t.noderef;
                json.typefileratache = t.typefileratache
                break;
            }
        }


        this.showFileInAlfresco(json);


    }


    showFileInAlfresco(data: any) {
        console.log("nodereffffffff", data);


        this.alfresco.getContentUrl(data.noderef)
            .then(
                (previewUrl: any) => {
                    console.log(previewUrl);


                    // let type='application/pdf';
                    if ((data.typefileratache).includes('office')) {
                        this.doc_viewer.visionneuse = "office"
                        //   this.modalRef = this.modalService.open(this.showPieceJoint, {size: 'lg'});


                        this.doc_viewer.pdfSrcc = previewUrl;
                        //  this.test=false;


                        this.docviewer.emit(this.doc_viewer);

                    } else if ((data.typefileratache).includes('pdf')) {
                        console.log("Autre types");
                        // window.open(previewUrl);
                        this.doc_viewer.visionneuse = "pdf"
                        this.doc_viewer.pdfSrcc = previewUrl;
                        //this.test=false;



                        this.docviewer.emit(this.doc_viewer);
                    }
                    else {
                        this.doc_viewer.visionneuse = 'pdf';
                        this.doc_viewer.pdfSrcc = previewUrl;
                        //   this.test=false;
                        this.docviewer.emit(this.doc_viewer);


                    }

                });

    }

    getdossierByid() {

        // this.dossierService.getdossierByid(this.iddossier).subscribe((data: any) => {


        // })

    }

    gelListPjdossier() {
        // this.dossierService.getdossierByid(this.iddossier).subscribe((datadossier: any) => {
        //     console.log("data===>", datadossier);
        //     this.datadossier = datadossier;
        //
        //     let typedossier = datadossier.typedossier;
        //     console.log("typedossier==>", typedossier);
        //
        //     let nomactivte = datadossier.activityname;
        //
        //
        //
        //     if(this.datadossier.activityname===null){
        //         this.dossierService.getAcessFicheClientstatut0(this.iddossier).subscribe((dataofacces:any)=>{
        //            // this.modeDocument=dataofacces;
        //
        //
        //             if (dataofacces === true) {
        //                 this.modedocument= true;
        //
        //             } else {
        //                 this.modedocument= false;
        //
        //
        //             }
        //
        //             console.log("dataofacces :::::::::::::::: act null ",dataofacces)
        //         })
        //     }else{
        //
        //         this.dossierService.getAcessFicheClient(this.iddossier).subscribe((dataofacces:any)=>{
        //             //this.modeDocument=dataofacces;
        //             console.log("dataofacces :::::::::::::::: act not null ",dataofacces)
        //
        //
        //
        //
        //             if (dataofacces === true) {
        //                 this.modedocument= true;
        //
        //             } else {
        //                 this.modedocument= false;
        //
        //
        //             }
        //
        //         })
        //     }
        //
        //
        //
        //
        //     this.pjService.getListPjInBD(this.iddossier).subscribe((dataPJ: any) => {
        //         console.log("dataPJ length "+dataPJ.length)
        //
        //         if (dataPJ.length === 0) {
        //             // this.PJrattacher = false;
        //
        //             this.getPiecejointeBytname(typedossier, datadossier);
        //
        //
        //         } else {
        //
        //             this.getpjsfromdb(this.iddossier);
        //         }
        //     })
        //
        //
        // });
    }

    gelListPjFicheCompte() {
        // this.dossierService.getcomptebyid(this.iddossier).subscribe((datacompte: any) => {
        //     console.log("data===>", datacompte)
        //     let categoriecompte = datacompte.categorie;
        //     console.log("Categorie==>", categoriecompte);
        //
        //     let nomactivte = datacompte.activityname;
        //
        //
        //     if(datacompte.activityname===null){
        //         this.dossierService.getAcessComptestatut0(this.iddossier).subscribe(resultacces => {
        //             if (resultacces === true) {
        //                 this.modedocument = true;
        //
        //             } else {
        //                 this.modedocument = false;
        //
        //
        //             }
        //         });
        //
        //
        //     }else {
        //
        //         this.dossierService.getAcessCompte(this.iddossier).subscribe(resultacces => {
        //             if (resultacces === true) {
        //                 this.modedocument = true;
        //
        //             } else {
        //                 this.modedocument = false;
        //
        //
        //             }
        //         });
        //     }
        //
        //
        //
        //     this.pjService.getListPj_InBD_bycompte(this.iddossier).subscribe((dataPJ: any) => {
        //
        //         if (dataPJ.length === 0) {
        //             // this.PJrattacher = false;
        //
        //             this.getPiecejointeByCategorieCompte(categoriecompte, datacompte);
        //
        //
        //         } else {
        //
        //             this.getpjsfromdb_compte(this.iddossier);
        //
        //             //}
        //
        //         }
        //     })
        //
        //
        //
        //
        //     // this.getPiecejointeByCategorieCompte(categoriecompte);
        //
        //
        // });
    }

    getPiecejointeBytname(name, datadossier) {
        console.log("dataof dossier",datadossier.fonctionPEP)

//         this.administrationService.getPiecejointeBytname(name).subscribe((data: any) => {
//             console.log("data piece jointe", data);
//
//             if (data.length!=0){
//
//
//                 this.pieceJoinNom1 = [] ;
//                 this.piecejointeliste=[]
//                 for (let i = 0; i < data.length; i++) {
//                    // if(datadossier.fonctionPEP==="Oui")
//                     this.pieceJoinNom1.push(
//                         {
//
//                             id: i,
//                             obligatoire: data[i].obligatoire,
//                             pieceafournir: data[i].pieceafournir,
//                             cocher: null,
//                             pjfournie: false,
//                             name: data[i].nompiece,
//                             nomfichierrattache: null,
//                             tableaufile: [],
//                             noderef: null,
//                             path: null,
//                             noderefparent: null,
//                             typefileratache: null,
//                             valider: null,
//                             dossierPJ: datadossier.dossierPJ,
//                             comptePJ: datadossier.comptePJ,
//                             version: null
//                         }
//                     )
//
//                 }
//                 let contactyear=this.DateObj.getFullYear() ;
// console.log("contactyear defaut",contactyear)
//                 let pathalfresco;
//                 if (this.datadossier.contactDate!=null){
//                     let contactdate = datadossier.contactDate;
//
//
//                      contactyear = contactdate.substring(0, 4);
//                 }
//                 pathalfresco = contactyear + "/" + datadossier.agence + "/";
//
//                 let path = contactyear + "/" + datadossier.agence + "/" + datadossier.codeclient;
//
//                 //let existpath=this.pathExistOrNot(path);
//
//
//                 this.pathExistOrNot(path).then((response: any) => {
//                 //let piecejointeliste =[] ;
//                     console.log("response :::::::: ",response)
//                     if (response == false) {
//
//                         console.log("condition 1 ");
//
//
//                         this.alfrescoApi.createFolderAutoRename(datadossier.codeclient, pathalfresco, this.parentNodeRef).then((dataa: any) => {
//                             console.log("dataa", dataa);
//                             //let myPromise = new Promise((resolve, reject) => {
//
//                             for (let t = 0; t < this.pieceJoinNom1.length; t++) {
//                                 // t.noderefparent = dataa.entry.id;
//
//
//                                 let piecejointe = new pj();
//                                 piecejointe.pieceafournir = this.pieceJoinNom1[t].pieceafournir;
//                                 piecejointe.path = pathalfresco;
//                                 piecejointe.obligatoire = this.pieceJoinNom1[t].obligatoire;
//                                 piecejointe.cocher = this.pieceJoinNom1[t].cocher;
//                                 piecejointe.nomfichierrattache = this.pieceJoinNom1[t].nomfichierrattache;
//                                 piecejointe.name = this.pieceJoinNom1[t].name;
//                                 piecejointe.noderef = this.pieceJoinNom1[t].noderef;
//                                 piecejointe.typefileratache = this.pieceJoinNom1[t].typefileratache;
//                                 piecejointe.noderefparent = dataa.entry.id;
//                                 piecejointe.pjfournie = this.pieceJoinNom1[t].pjfournie;
//                                 piecejointe.dossierPJ = datadossier;
//                                 piecejointe.comptePJ = null;
//                                 piecejointe.valider = this.pieceJoinNom1[t].valider;
//                                 piecejointe.version = this.pieceJoinNom1[t].version;
//                                 this.piecejointeliste.push(piecejointe)
//                                 // this.savepj(piecejointe);
//
//                             }
//
//                             if (this.piecejointeliste.length!=0) {
//
//                                 this.pjService.addPiecejointesliste(this.piecejointeliste).subscribe((data: any) => {
//
//                                     console.log("data save piece jointe ", data);
//                                     // if (t == this.pieceJoinNom1.length-1) {
//                                     console.log("innnnnn condition ");
//                                     this.getpjsfromdb(this.iddossier);
//
//                                     // }
//
//                                 })
//                             }
//
//
//                         })
//
//
//                     } else {
//                         console.log("condition 2 ");
//
//                         this.alfresco.getNodeInfobyPath(this.parentNodeRef, path).then((result: any) => {
//
//
//                             for (let t = 0; t < this.pieceJoinNom1.length; t++) {
//                                 // t.noderefparent = dataa.entry.id;
//
//
//                                 let piecejointe = new pj();
//                                 piecejointe.pieceafournir = this.pieceJoinNom1[t].pieceafournir;
//                                 piecejointe.path = pathalfresco;
//                                 piecejointe.obligatoire = this.pieceJoinNom1[t].obligatoire;
//                                 piecejointe.cocher = this.pieceJoinNom1[t].cocher;
//                                 piecejointe.nomfichierrattache = this.pieceJoinNom1[t].nomfichierrattache;
//                                 piecejointe.name = this.pieceJoinNom1[t].name;
//                                 piecejointe.noderef = this.pieceJoinNom1[t].noderef;
//                                 piecejointe.typefileratache = this.pieceJoinNom1[t].typefileratache;
//                                 piecejointe.noderefparent = result.id;
//                                 piecejointe.pjfournie = this.pieceJoinNom1[t].pjfournie;
//                                 piecejointe.dossierPJ = datadossier;
//                                 piecejointe.comptePJ = null;
//                                 piecejointe.valider = this.pieceJoinNom1[t].valider;
//                                 piecejointe.version = this.pieceJoinNom1[t].version;
//                                 this.piecejointeliste.push(piecejointe)
//
//                                 // this.savepj(piecejointe);
//                             }
// if (this.piecejointeliste.length!=0){
//     this.pjService.addPiecejointesliste(this.piecejointeliste).subscribe((data: any) => {
//
//         console.log("data save piece jointe ", data);
//         // if (t == this.pieceJoinNom1.length - 1) {
//
//         this.getpjsfromdb(this.iddossier);
//
//         // }
//
//     })
// }
//
//
//
//                             //this.getpjsfromdb(this.iddossier);
//                         })
//
//
//                     }
//
//
//                 })
//
//
//                 this.messageEvent.emit(this.pieceJoinNom1);
//
//             }
//         })

    }



    getpjsfromdb_compte(compte) {
console.log("Compte::::::::",compte)
        this.PJservice.getListPj_InBD_bycompte(compte).subscribe((data: any) => {
            console.log("table PJS", data);
            this.dataSourcePj = [];

            let activiteName = data[0].comptePJ['activityname'];
            console.log(":::::::::::::::::::::::::::::::", activiteName)
            if (activiteName == null || activiteName == 'Rattacher les PJs') {
                console.log("innnnnnnnnnnnnnnnn true ");
                this.activitevisibilite = true;

                // let plus =document.getElementsByClassName("dx-icon dx-icon-add") ;
                //  plus.item(0).setAttribute('hidden','true')
                // console.log("plussssssssssss",plus  ) ;
                document.getElementsByClassName("dx-icon dx-icon-add")[0].setAttribute('style', 'display:block');
                // document.getElementById('gridList').setAttribute('onTool')


            }

            else {
                console.log("innnnnnnnfnnnnnnnnn false ");

                // let plus =document.getElementsByClassName("dx-icon dx-icon-add") ;
                // plus.item(0).setAttribute('hidden','false');

                document.getElementsByClassName("dx-icon dx-icon-add")[0].setAttribute('style', 'display:none');

                this.activitevisibilite = false;
            }


            this.comptepj = data[0].comptePJ;
            // this.dossierpj=

            console.log("datasourcepj ===>", this.dataSourcePj);
            let i = 0
            for ( i; i < data.length; i++) {

                this.dataSourcePj.push(
                    {

                        id: data[i].id,
                        obligatoire: data[i].obligatoire,

                        //Ajouter par helmi ligne 52
                        pieceafournir: data[i].pieceafournir,

                        cocher: data[i].cocher,
                        pjfournie: data[i].pjfournie,
                        name: data[i].name,
                        nomfichierrattache: data[i].nomfichierrattache,
                        noderef: data[i].noderef,
                        path: data[i].path,
                        noderefparent: data[i].noderefparent,
                        typefileratache: data[i].typefileratache,
                        valider: data[i].valider,
                        tableaufile: [],
                        dossierPJ: data[i].dossierPJ,
                        comptePJ:data[i].comptePJ,
                        activite: data[i].comptePJ['activityname'],
                        version: data[i].version,
                        modedocument:this.modedocument

                    }
                )
// if (i==data.length){
                    this.initdoc_viewer();
               // }
            }
            console.log("push with modedocument:::::",this.dataSourcePj)


            if (i==data.length-1){
                this.initdoc_viewer();
            }
         //   this.initdoc_viewer();
            this.messageEvent.emit(this.dataSourcePj);
            //this.checkBoxValue=data.cocher;
        })

    }



    getpjsfromdb(dossier) {
console.log("dossier::::::::::::::",dossier)

        this.PJservice.getListPjInBD(dossier).subscribe((data: any) => {
            console.log("table PJS", data);
            this.dataSourcePj = [];

            let activiteName = data[0].dossierPJ['activityname'];
            console.log(":::::::::::::::::::::::::::::::", activiteName) ;
            if((activiteName == null || activiteName == 'Rattacher les PJs') && (this.modedocument)){
                console.log("innnnnnnnnnnnnnnnn true ");
                this.activitevisibilite = true;

                // let plus =document.getElementsByClassName("dx-icon dx-icon-add") ;
                //  plus.item(0).setAttribute('hidden','true')
                // console.log("plussssssssssss",plus  ) ;
                document.getElementsByClassName("dx-icon dx-icon-add")[0].setAttribute('style', 'display:block');
                // document.getElementById('gridList').setAttribute('onTool')


            }

            else {
                console.log("innnnnnnnnnnnnnnnn false ");

                document.getElementsByClassName("dx-icon dx-icon-add")[0].setAttribute('style', 'display:none');

                this.activitevisibilite = false;
            }


            this.dossierpj = data[0].dossierPJ;
            // this.dossierpj=

            console.log("datasourcepj ===>", this.dataSourcePj);
            let i = 0
            for ( i ; i < data.length; i++) {

                this.dataSourcePj.push(
                    {

                        id: data[i].id,
                        obligatoire: data[i].obligatoire,

                        //Ajouter par helmi ligne 52
                        pieceafournir: data[i].pieceafournir,

                        cocher: data[i].cocher,
                        pjfournie: data[i].pjfournie,
                        name: data[i].name,
                        nomfichierrattache: data[i].nomfichierrattache,
                        noderef: data[i].noderef,
                        path: data[i].path,
                        noderefparent: data[i].noderefparent,
                        typefileratache: data[i].typefileratache,
                        valider: data[i].valider,
                        tableaufile: [],
                        dossierPJ: data[i].dossierPJ,
                        activite: data[i].dossierPJ['activityname'],
                        version: data[i].version,
                        modedocument:this.modedocument

                    }
                )


                    this.initdoc_viewer();


                console.log("push with modedocument:::::",this.dataSourcePj)

            }

            if (i==data.length-1){
                console.log("in doc ::::2")
                this.initdoc_viewer();
            }

            //this.checkBoxValue=data.cocher;
            // this.initdoc_viewer();
            this.messageEvent.emit(this.dataSourcePj);


        })

    }


    getPiecejointeByCategorieCompte(name,datacompte) {

        console.log("nameee",name) ;

        // this.administrationService.getPiecejointeByCategorie(name).subscribe((data: any) => {
        //     console.log("data piece jointe", data);
        //     this.pieceJoinNom1=[] ;
        //     for (let i = 0; i < data.length; i++) {
        //
        //         this.pieceJoinNom1.push(
        //             {
        //
        //                 id: i,
        //                 obligatoire: data[i].obligatoire,
        //                 pieceafournir: data[i].pieceafournir,
        //                 cocher: null,
        //                 pjfournie: false,
        //                 name: data[i].nompiece,
        //                 NomFile: null,
        //                 tableaufile: [],
        //                 noderef: null,
        //                 path: null,
        //                 noderefparent: null,
        //                 typefileratache: null,
        //                 valider: null,
        //                 dossierPJ: datacompte.dossierPJ,
        //                 comptePJ: datacompte.comptePJ,
        //                 version: null
        //
        //             }
        //         )
        //
        //
        //     }
        //
        //
        //
        //     let pathalfresco;
        //     let contactdate;
        //     let contactyear=null;
        //     this.dossierService.getficheclientbycodeclient(datacompte.codeclient).subscribe((dataofclient: any) => {
        //         if (dataofclient.contactDate!=null) {
        //
        //
        //             contactdate = dataofclient.contactDate;
        //
        //
        //             contactyear = contactdate.substring(0, 4);
        //         }
        //         pathalfresco = contactyear + "/" + dataofclient.agence + "/" + dataofclient.codeclient+"/"+datacompte.numcompte;
        //
        //
        //
        //         // }, error => {
        //         //     this.bloquesave = true;
        //         // });
        //
        //         let   path = contactyear + "/" + dataofclient.agence + "/" + dataofclient.codeclient
        //         let piecejountelist=[]
        //
        //         this.pathExistOrNot(pathalfresco).then((response:any)=>{
        //             console.log("existpath::::",response);
        //             if(response==false){
        //                 this.alfrescoApi.createFolderAutoRename(datacompte.numcompte, path, this.parentNodeRef).then((datafolder: any) => {
        //
        //                     for (let t = 0; t < this.pieceJoinNom1.length; t++) {
        //                         // t.noderefparent = dataa.entry.id;
        //
        //
        //                         let piecejointe = new pj();
        //                         piecejointe.pieceafournir = this.pieceJoinNom1[t].pieceafournir;
        //                         piecejointe.path = pathalfresco;
        //                         piecejointe.obligatoire = this.pieceJoinNom1[t].obligatoire;
        //                         piecejointe.cocher = this.pieceJoinNom1[t].cocher;
        //                         piecejointe.nomfichierrattache = this.pieceJoinNom1[t].nomfichierrattache;
        //                         piecejointe.name = this.pieceJoinNom1[t].name;
        //                         piecejointe.noderef = this.pieceJoinNom1[t].noderef;
        //                         piecejointe.typefileratache = this.pieceJoinNom1[t].typefileratache;
        //                         piecejointe.noderefparent = datafolder.entry.id;
        //                         piecejointe.pjfournie = this.pieceJoinNom1[t].pjfournie;
        //                         piecejointe.dossierPJ = null;
        //                         piecejointe.comptePJ = datacompte;
        //                         piecejointe.valider = this.pieceJoinNom1[t].valider;
        //                         piecejointe.version = this.pieceJoinNom1[t].version;
        //                         piecejountelist.push(piecejointe)
        //                         //this.savepj(piecejointe);
        //                     }
        //
        //                     if (piecejountelist.length!=0){
        //                         this.pjService.addPiecejointesliste(piecejountelist).subscribe((data: any) => {
        //
        //                             console.log("data save piece jointe ", data);
        //
        //
        //                             // if (t==this.pieceJoinNom1.length-1){
        //
        //
        //
        //                             this.getpjsfromdb_compte(this.iddossier);
        //
        //
        //                             // }
        //
        //
        //                         })
        //                     }
        //
        //                         // this.getpjsfromdb_compte(this.iddossier);
        //
        //
        //
        //                 })
        //
        //
        //
        //             }
        //             else{
        //
        //
        //
        //
        //                 this.alfresco.getNodeInfobyPath(this.parentNodeRef, pathalfresco).then((result: any) => {
        //
        //                     console.log("result node info ==== ", result.id) ;
        //                     console.log("pjjjjjjjjj ",this.pieceJoinNom1);
        //
        //                     for (let t = 0; t < this.pieceJoinNom1.length; t++) {
        //
        //
        //                         let piecejointe = new pj();
        //                         piecejointe.pieceafournir = this.pieceJoinNom1[t].pieceafournir;
        //                         piecejointe.path = pathalfresco;
        //                         piecejointe.obligatoire = this.pieceJoinNom1[t].obligatoire;
        //                         piecejointe.cocher = this.pieceJoinNom1[t].cocher;
        //                         piecejointe.nomfichierrattache = this.pieceJoinNom1[t].nomfichierrattache;
        //                         piecejointe.name = this.pieceJoinNom1[t].name;
        //                         piecejointe.noderef = this.pieceJoinNom1[t].noderef;
        //                         piecejointe.typefileratache = this.pieceJoinNom1[t].typefileratache;
        //                         piecejointe.noderefparent = result.id;
        //                         piecejointe.pjfournie = this.pieceJoinNom1[t].pjfournie;
        //                         piecejointe.dossierPJ = null;
        //                         piecejointe.comptePJ = datacompte;
        //                         piecejointe.valider = this.pieceJoinNom1[t].valider;
        //                         piecejointe.version = this.pieceJoinNom1[t].version;
        //                         piecejountelist.push(piecejointe);
        //                         //  this.savepj(piecejointe);
        //
        //                     }
        //
        //                     if (piecejountelist.length!=0){
        //
        //                         this.pjService.addPiecejointesliste(piecejountelist).subscribe((data: any) => {
        //
        //                             console.log("data save piece jointe ", data);
        //
        //
        //                             // if (t==this.pieceJoinNom1.length-1){
        //
        //
        //
        //                             this.getpjsfromdb_compte(this.iddossier);
        //
        //
        //                             // }
        //
        //
        //                         })
        //                     }
        //
        //
        //                         // this.getpjsfromdb_compte(this.iddossier);
        //
        //
        //
        //
        //
        //
        //                     // if (this.cle == "fromcompte") {
        //                     //
        //                     //     this.getpjsfromdb_compte(this.iddossier);
        //                     //
        //                     // }
        //                 });
        //             }
        //
        //
        //             this.messageEvent.emit(this.pieceJoinNom1);
        //
        //         })
        //     })
        // })
    }



    addcheckboxvalue(e, data) {
        this.index = this.dataSourcePj.indexOf(data)
        console.log("*******event*********", e, "********data.data***********", data);
        console.log("datasource finale before=====> ", this.dataSourcePj);
        console.log("data.id =====> ", data.id);
        this.dataSourcePj[this.index].cocher = e.value;
        console.log("datasource finale after =====> ", this.dataSourcePj);

        this.updatepj(this.dataSourcePj[this.index]);

        this.messageEvent.emit(this.dataSourcePj);

    }

    saveonlyonepiece(piecejointe) {


        this.pjService.addPiecejointes(piecejointe).subscribe((data: any) => {

            console.log("data save piece jointe ", data);
            if(this.cle === "fromdossier"){
                this.getpjsfromdb(this.iddossier);
            }
           if (this.cle === "fromcompte"){
                this.getpjsfromdb_compte(this.iddossier);

            }

        })


    }

    savepj(piecejointe) {


        this.pjService.addPiecejointes(piecejointe).subscribe((data: any) => {

            console.log("data save piece jointe ", data);


        })


    }


    base64ToArrayBuffer(base64) {
        var binary_string = window.atob(base64);
        var len = binary_string.length;
        var bytes = new Uint8Array(len);
        for (var i = 0; i < len; i++) {
            bytes[i] = binary_string.charCodeAt(i);
        }
        return bytes.buffer;
    }


    click(index): any {



        // this.index = index.id
        this.index = this.dataSourcePj.indexOf(index);


    }


    closemodalvisualisation() {

        this.modalReference.close();

    }


    fileChangenewversion(input) {
        this.tableau = [];
        //this.hiddenNameFile = false;

        this.readFiles(input.files);
        console.log('filesssss', input.files);
        let tab = [];
        for (let t of input.files) {
            tab[tab.length] = t.name;
            console.log('t:    ', t);


            this.tableau[this.tableau.length] = t;


        }
        console.log('this.index', tab[0]);
        console.log("datasource finale before=====> ", this.dataSourcePj);

        this.tableaufile[this.index] = this.tableau;


        this.dataSourcePj[this.index].tableaufile[0] = this.tableau;
        this.dataSourcePj[this.index].pjfournie = true;
        console.log("datasource pj===>", this.dataSourcePj);
        this.dataSourcePj[this.index].nomfichierrattache = tab[0];


        //this.alfresco.importwithnewversion()


        this.messageEvent.emit(this.dataSourcePj);


        this.testtab[this.index] = tab;

        console.log("file ::::: ", this.dataSourcePj[this.index].tableaufile[0][0].type, "noderefffff:::::", this.dataSourcePj[this.index].noderef);


        this.servicefile.uploadnewversion(this.dataSourcePj[this.index].tableaufile[0][0], this.dataSourcePj[this.index].noderef).subscribe((data: any) => {
            console.log("data upload new version ", data);

            // this.dataSourcePj[this.index].nomfichierrattache=data.fileName ;


         //   "incorrect minetype!"


            if (data!="incorrect minetype!"){

            this.updatepj(this.dataSourcePj[this.index]);


            this.initdoc_viewer() ;

            }
            else {
                this.toster.error("type du fichier incompatible")
            }


        })


        this.messageEvent.emit(this.dataSourcePj);
        console.log('2');


        ///this.pieceJoinNom1.NomFile=this.testtab
        console.log('tableauuuu dataGrid', this.testtab);


        this.viderinput(input)

    }

    fileChange(input) {

        this.tableau = [];
        //this.hiddenNameFile = false;

        this.readFiles(input.files);
        console.log('filesssss', input.files);
        let tab = [];
        for (let t of input.files) {
            tab[tab.length] = t.name;
            console.log('t:    ', t);


            this.tableau[this.tableau.length] = t;


        }
        console.log('this.index', tab[0]);
        console.log("datasource finale before=====> ", this.dataSourcePj);

        this.tableaufile[this.index] = this.tableau;


        this.dataSourcePj[this.index].tableaufile[0] = this.tableau;
        this.dataSourcePj[this.index].pjfournie = true;
        console.log("datasource pj===>", this.dataSourcePj);
        this.dataSourcePj[this.index].nomfichierrattache = tab[0];
        this.dataSourcePj[this.index].cocher =true ;

        //this.alfresco.importwithnewversion()


        this.messageEvent.emit(this.dataSourcePj);


        this.testtab[this.index] = tab;

        console.log("file ::::: ", this.dataSourcePj[this.index].tableaufile[0][0], "noderefffff:::::", this.dataSourcePj[this.index].noderefparent);


        this.alfresco.toUploadFilesautorename(this.dataSourcePj[this.index].noderefparent, null, this.dataSourcePj[this.index].tableaufile[0][0]).then((datauploadfile: any) => {
            console.log("data:::upload::file ", datauploadfile)
            this.dataSourcePj[this.index].noderef = datauploadfile.entry.id;
            this.dataSourcePj[this.index].typefileratache = datauploadfile.entry.content.mimeType;
            console.log("noderef ::: ", datauploadfile.entry.id);

            this.updatepj(this.dataSourcePj[this.index]);

        })


        console.log('2');


        ///this.pieceJoinNom1.NomFile=this.testtab
        console.log('tableauuuu dataGrid', this.testtab);

        this.messageEvent.emit(this.dataSourcePj);
        this.viderinput(input)


    }

    updatepj(data) {


        this.pjService.updatePiecejointe(data).subscribe((data: any) => {

            console.log("data update piece jointe  ", data);


        })


    }


    addcheckboxvaluevalidation(e, data) {

        this.index = this.dataSourcePj.indexOf(data)
        console.log("*******event*********", e, "********data.data***********", data);
        console.log("datasource finale before=====> ", this.dataSourcePj);
        console.log("data.id =====> ", data.id);
        this.dataSourcePj[this.index].valider = e.value;
        console.log("datasource finale after =====> ", this.dataSourcePj);

        this.updatepj(this.dataSourcePj[this.index]);

        this.messageEvent.emit(this.dataSourcePj);


        console.log("datasourcepj ====> ", this.dataSourcePj);


    }


    viderinput(input) {

        input.value = '';

    }

    readFiles(files, index = 0) {
        let reader = new FileReader();

        if (index in files) {
            console.log("in read files ", files.name);
            //this.testscan=false;
            this.readFile(files[index], reader, (result) => {


                this.readFiles(files, index + 1);
            });

        } else {
            this.changeDetectorRef.detectChanges();
        }
    }

    scannFileversion(data) {

        this.servicefile.getuserprofile(sessionStorage.getItem("datalogin")).then(
            datauserprofile => {

                console.log(datauserprofile);

/// le if du null ici
// test if profile exist
                if (datauserprofile == null) {


                    document.getElementById('tt').click();

                } else {
//******************************* else open modal file name modal **********************************


                    console.log("inn else ", data);

                    this.click(data);
                    //  this.datauserprofile = data;


                    this.scanversion(this.dataSourcePj[this.index].nomfichierrattache, datauserprofile);


                }


            },
            error => console.log(error)
        );
    }

    scannFile(data) {

        this.servicefile.getuserprofile(sessionStorage.getItem("datalogin")).then(
            datauserprofile => {

                console.log(datauserprofile);

/// le if du null ici
// test if profile exist
                if (datauserprofile == null) {


                    document.getElementById('tt').click();

                } else {

                    this.click(data);
                    //  this.datauserprofile = data;

                    console.log("indexxxxxxxxxxxxxxxxxxxx::::::::::::::::",this.index) ;

                    this.scan(this.dataSourcePj[this.index].name, datauserprofile);


                }


            },
            error => console.log(error)
        );
    }

    scan2(name,data,confirm)
    {
        let decision =false;
        if(confirm=="2" || confirm=="1")

            decision  =true;


        this.loadIndicatorVisible=true;
        this.servicefile.Scan('Acquire',confirm, data.nomscanner, 'false', '1', data.typedudocument, data.rectoverso, data.resolution, data.mode, '1', data.tailledufichier).then(res => {
            console.log('res', res);
            this.loadIndicatorVisible=false;

            //  this.testscann = true;
            // this.name1 = name.substring(0, name.lastIndexOf('.'));
            // name= this.serviceFile.nomingrid

            let namefilescan;
            if (name=="Autre"){


                namefilescan = name + '.pdf';
            }
            else {


                namefilescan = 'Piéce' + '.pdf';


            }


            const byteArray = new Uint8Array(atob(res.result.data).split('').map(char => char.charCodeAt(0)));
            const f1 = new File([byteArray], namefilescan, {type: 'application/pdf'});
            console.log(f1);


            this.arraybuffer = this.base64ToArrayBuffer(res.result.data);


            // if (this.dataSourcePj[this.index].noderef == null) {
            // console.log('inn upload en mode edit in cuurent node');
            // this.alfresco.toUploadFilesautorename(this.dataSourcePj[this.index].noderefparent, null, f1).then((datauploadfile: any) => {
            //     console.log("data:::upload::file ", datauploadfile)
            //
            //     this.dataSourcePj[this.index].noderef = datauploadfile.entry.id;
            //     this.dataSourcePj[this.index].typefileratache = datauploadfile.entry.content.mimeType;
            //     console.log("noderef ::: ", datauploadfile.entry.id)
            //
            //     this.dataSourcePj[this.index].nomfichierrattache = datauploadfile.entry.name;
            //
            //
            //     this.updatepj(this.dataSourcePj[this.index]);
            //
            // })
            //
            // this.messageEvent.emit(this.dataSourcePj);
            //  }
            /*  else {
                  this.servicefile.uploadnewversion(f1, this.dataSourcePj[this.index].noderef, 'application/pdf').subscribe((data: any) => {
                      console.log('response uploadnew version ', data);

                      //      this.dataSourcePj[this.index].no===
                      this.dataSourcePj[this.index].nomfichierrattache = namefilescan;

                      this.updatepj(this.dataSourcePj[this.index]);

                  });

                  this.messageEvent.emit(this.dataSourcePj);
              }*/


            // }).then(res=>{


            if (decision){

                do {

                    //  this.modal.open(this.confirmmodal, {size: 'sm'});


                    decision = window.confirm("Veuillez charger un papier si vous voulez scanner plus de documents");

                    // prompt(message?: string, _default?: string): string | null;



                    console.log("decision :::::: ",decision);

                    if (decision) {

                        console.log("in scan2 with comfirm 2 ") ;
                        this.scan2(name,data,"2");
                        break;
                    }
                    else{

                        decision=false;
                        break ;
                    }

                }
                while(decision)
            }




            if (!decision ){
                console.log("decision :::::: with comfirm 0 ") ;

                //   var c =  this.scan2(name,data,"0");
                decision = false;



                console.log('inn upload en mode edit in cuurent node');
                this.alfresco.toUploadFilesautorename(this.dataSourcePj[this.index].noderefparent, null, f1).then((datauploadfile: any) => {
                    console.log("data:::upload::file ", datauploadfile);

                    this.dataSourcePj[this.index].noderef = datauploadfile.entry.id;
                    this.dataSourcePj[this.index].typefileratache = datauploadfile.entry.content.mimeType;
                    console.log("noderef ::: ", datauploadfile.entry.id);
                    this.dataSourcePj[this.index].pjfournie = true;
                    this.dataSourcePj[this.index].cocher = true;
                    this.dataSourcePj[this.index].nomfichierrattache = datauploadfile.entry.name;


                    this.updatepj(this.dataSourcePj[this.index]);

                })

                this.messageEvent.emit(this.dataSourcePj);


            }



        }    ,
                err => {
                    this.loadIndicatorVisible=false;
                  console.log("error", err.error.result);
                  //  result: {errorMessage
                  //  this.toster.error("Erreur au cours de la numérisation veuillez vérifier votre scanner ") ;
                    this.toster.error(err.error.result)


                }

        );

        this.messageEvent.emit(this.dataSourcePj);
    }







    scan3(name,data,confirm) {
        let decision =false;
        if(confirm=="2" || confirm=="1")

            decision  =true;

        this.servicefile.Scan('Acquire',confirm, data.nomscanner, 'false', '1', data.typedudocument, data.rectoverso, data.resolution, data.mode, '1', data.tailledufichier).then(res => {
            console.log('res', res);
            //  this.testscann = true;

            // this.name1 = name.substring(0, name.lastIndexOf('.'));
            // name= this.serviceFile.nomingrid

            //
            // console.log("name",name) ;
            // let namefilescan = name + '.pdf';
            //
            // let namefilescantemp;
            // if (name=="Autre"){
            //
            //
            //     namefilescantemp = name + '.pdf';
            // }
            // else {
            //
            //
            //     namefilescantemp = 'Piéce' + '.pdf';
            //
            //
            // }
            const byteArray = new Uint8Array(atob(res.result.data).split('').map(char => char.charCodeAt(0)));
            const f1 = new File([byteArray], name, {type: 'application/pdf'});
            console.log(f1);


            this.arraybuffer = this.base64ToArrayBuffer(res.result.data);


            // if (this.dataSourcePj[this.index].noderef == null) {
            console.log('inn upload en mode edit in cuurent node');
            // this.alfresco.toUploadFilesautorename(this.dataSourcePj[this.index].noderefparent, null, f1).then((datauploadfile: any) => {
            //     console.log("data:::upload::file ", datauploadfile)
            //
            //     this.dataSourcePj[this.index].noderef = datauploadfile.entry.id;
            //     this.dataSourcePj[this.index].typefileratache = datauploadfile.entry.content.mimeType;
            //     console.log("noderef ::: ", datauploadfile.entry.id)
            //
            //     this.dataSourcePj[this.index].nomfichierrattache = datauploadfile.entry.name;
            //
            //
            //     this.updatepj(this.dataSourcePj[this.index]);
            //
            // })

            //  this.messageEvent.emit(this.dataSourcePj);
            //  }
            // else {
            //       this.servicefile.uploadnewversion(f1, this.dataSourcePj[this.index].noderef, 'application/pdf').subscribe((data: any) => {
            //           console.log('response uploadnew version ', data);
            //
            //           //      this.dataSourcePj[this.index].no===
            //           this.dataSourcePj[this.index].nomfichierrattache = namefilescan;
            //
            //           this.updatepj(this.dataSourcePj[this.index]);
            //
            //       });
            //       //
            //       this.messageEvent.emit(this.dataSourcePj);
            //   }*/


            // }).then(res=>{


            if (decision){

                do {


                    //this.confirmmodal.

                    // this.modal.open(this.confirmmodal, {size: 'sm'});



                    decision = window.confirm("Veuillez charger un papier si vous voulez scanner plus de documents");


                    console.log("decision :::::: ",decision);

                    if (decision) {

                        console.log("in scan2 with comfirm 2 ") ;
                        this.scan2(name,data,"2");
                        break;
                    }
                    else{

                        decision=false;
                        break ;
                    }

                }
                while(decision)
            }




            if (!decision ){
                console.log("decision :::::: with comfirm 0 ") ;

                //   var c =  this.scan2(name,data,"0");
                decision = false;


                this.servicefile.uploadnewversion(f1, this.dataSourcePj[this.index].noderef).subscribe((data: any) => {
                    console.log('response uploadnew version ', data);

                    //      this.dataSourcePj[this.index].no===
                   // this.dataSourcePj[this.index].nomfichierrattache = name;





                    if (data!="incorrect minetype!"){

                        this.updatepj(this.dataSourcePj[this.index]);


                        this.initdoc_viewer() ;

                    }
                    else {
                        this.toster.error("type du fichier incompatible")
                    }







                });
                //
                this.messageEvent.emit(this.dataSourcePj);




            }



        });

        this.messageEvent.emit(this.dataSourcePj);
    }


    scan(name, data) {


        console.log("innn scan ");
        this.scan2(name,data,"1");


    }



    scanversion(name, data) {


        console.log("innn scan ");
        this.scan3(name,data,"1");


    }



    pdfviewrindex(index) {

        console.log('pdfviewrr', index);
        console.log('buffertab', this.bufferarray);
        // this.index = this.pieceJoinNom1.indexOf(index);
        //  this.pdfsrcc = this.bufferarray[this.index];
        this.pdfsrcc = this.bufferarray[index];

    }


    pdfviewerr() {
        this.pdfsrc = this.arraybuffer;
    }


    removeImage(tab, index): void {
        // let index = this.index;
        this.index = this.dataSourcePj.indexOf(index);
        //this.tab2[this.index]="";

        //26
        // this.pieceJoinNom1[index].NomFile = "";
        this.dataSourcePj[this.index].nomfichierrattache = null;
        this.dataSourcePj[this.index].cocher =false ;
        this.dataSourcePj[this.index].pjfournie = false;
        this.dataSourcePj[this.index].tableaufile = [];
        this.messageEvent.emit(this.pieceJoinNom1);
        this.alfrescoApi.deleteNode(this.dataSourcePj[this.index].noderef);


        let piecejointe = new pj();


        piecejointe.id = this.dataSourcePj[this.index].id;
        piecejointe.pieceafournir = this.dataSourcePj[this.index].pieceafournir;
        piecejointe.path = this.dataSourcePj[this.index].path;
        piecejointe.obligatoire = this.dataSourcePj[this.index].obligatoire;
        piecejointe.cocher = this.dataSourcePj[this.index].cocher;
        piecejointe.nomfichierrattache = this.dataSourcePj[this.index].nomfichierrattache;
        piecejointe.name = this.dataSourcePj[this.index].name;
        piecejointe.noderef = null;
        piecejointe.typefileratache = null;
        piecejointe.noderefparent = this.dataSourcePj[this.index].noderefparent;
        piecejointe.pjfournie = this.dataSourcePj[this.index].pjfournie;
        piecejointe.dossierPJ = this.dataSourcePj[this.index].dossierPJ;
        piecejointe.comptePJ = this.dataSourcePj[this.index].comptePJ;
        piecejointe.valider = this.dataSourcePj[this.index].valider;
        piecejointe.version = this.dataSourcePj[this.index].version;
        this.updatepj(piecejointe);


        this.messageEvent.emit(this.dataSourcePj);




    }


    onAcquireImagee(e) {
    }

    readFile(file, reader, callback) {
        reader.onload = () => {
            callback(reader.result);
        };
        reader.readAsDataURL(file);


        console.log("readerrrrrr", reader);
    }




    hide_show_eye_grid(index): any {

        let showhide;
        // let index = this.index;
        if (((this.bufferarray[index]) == undefined) || (this.bufferarray[index].length == 0)) {

            showhide = false
        }
        else if (((this.bufferarray[index]) != undefined)) {
            showhide = true;

        }

        return showhide

    }


    hide_show_delete_grid(index): any {

        let showhide;
        //let index = this.index;


        if (((this.tableaufile[index]) == undefined) || (this.tableaufile[index].length == 0)) {

            showhide = false
        }
        else if (((this.tableaufile[index]) != undefined)) {
            showhide = true;

        }

        return showhide

    }


    visualisationscangrid(index) {


        // let index = this.index;


        console.log("bufferarray ===>", this.bufferarray);

        let length = this.bufferarray.length;
        // this.index = this.pieceJoinNom1.indexOf(index);

        console.log('indexxx', this.index);


        if ((this.bufferarray[index] == undefined) || (index > length)) {


            console.log('blockkk');

        } else if (this.bufferarray[index].length == 0) {

        } else {
            this.modalReference = this.modal.open(this.visualisation, {size: 'lg'});


            this.pdfviewrindex(index);

        }


    }


    addrow() {

        if (this.dataSourcePj.length!=0) {
            if (this.cle === "fromdossier") {


                let include = [];
                for (let i = 0; i < this.dataSourcePj.length; i++) {


                    include[include.length] = this.dataSourcePj[i].name.includes('Autre');
                }

                console.log("tableau include ", include);

                if (include.includes(true)) {
                    console.log("exist");
                    this.menu1 = this.dataSourcePj;
                    let i = 0;
                    let test = false;
                    do {
                        test = true;
                        if ((this.menu1[i].name === "Autre")) {
                            if ((this.menu1[i].nomfichierrattache === "") || (this.menu1[i].nomfichierrattache === null)) {

                                console.log('block g');
                                test = false;
                                this.toster.error("Veuillez entrer la piéce nomée Autre");
                                break;

                            }
                            else {
                                test = true
                            }
                        }
                        i++;
                    } while ((test === true) && (i < this.menu1.length));

                    if (test === true) {
                        var d = this.dataSourcePj[(this.dataSourcePj.length) - 1].id + 1;


                        console.log("iddddddddd", d);


                        this.dataSourcePj.push({
                            name: 'Autre',
                            pieceafournir: null,
                            nomfichierrattache: null,
                            pjfournie: null,
                            cocher: null,
                            tableaufile: [],
                            typefileratache: null,
                            valider: null,
                            noderef: null,
                            path: null,
                            nodrefparent: null,
                            activite: this.dossierpj['activityname'],
                            dossierPJ: this.dossierpj,
                            comptePJ: null,
                            version: null


                        });

                        //this.messageEvent.emit(this.pieceJoinNom1);


                        // t.noderefparent = dataa.entry.id;

                        if (this.dataSourcePj[this.dataSourcePj.length - 1].name == "Autre") {
                            let piecejointe = new pj();
                            piecejointe.pieceafournir = this.dataSourcePj[this.dataSourcePj.length - 1].pieceafournir;
                            piecejointe.path = this.dataSourcePj[this.dataSourcePj.length - 1].path;
                            piecejointe.obligatoire = this.dataSourcePj[this.dataSourcePj.length - 1].obligatoire;
                            piecejointe.cocher = this.dataSourcePj[this.dataSourcePj.length - 1].cocher;
                            piecejointe.nomfichierrattache = this.dataSourcePj[this.dataSourcePj.length - 1].nomfichierrattache;
                            piecejointe.name = this.dataSourcePj[this.dataSourcePj.length - 1].name;
                            piecejointe.noderef = this.dataSourcePj[this.dataSourcePj.length - 1].noderef;
                            piecejointe.typefileratache = this.dataSourcePj[this.dataSourcePj.length - 1].typefileratache;
                            piecejointe.noderefparent = this.dataSourcePj[0].noderefparent;
                            piecejointe.pjfournie = this.dataSourcePj[this.dataSourcePj.length - 1].pjfournie;
                            piecejointe.dossierPJ = this.dataSourcePj[this.dataSourcePj.length - 1].dossierPJ;
                            piecejointe.comptePJ = this.dataSourcePj[this.dataSourcePj.length - 1].comptePJ;
                            piecejointe.valider = this.dataSourcePj[this.dataSourcePj.length - 1].valider;
                            piecejointe.version = this.dataSourcePj[this.dataSourcePj.length - 1].version;

                            this.saveonlyonepiece(piecejointe);

                            this.messageEvent.emit(this.dataSourcePj);
                        }

                    }
                }

                else {
                    console.log("not exist ", this.dataSourcePj);
                    // var d = new Date();
                    //  console.log(this.pieceJoinNom1[(this.pieceJoinNom1.length) - 1].id + 1);
                    var d = this.dataSourcePj[(this.dataSourcePj.length) - 1].id + 1
                    console.log("iddddddddd", d);


                    // id: i,
                    //     obligatoire:data[i].obligatoire,
                    // pieceafournir:data[i].pieceafournir,
                    // cocher: null,
                    // pjfournie:false ,
                    // name: data[i].nompiece,
                    // NomFile: null,
                    // tableaufile:[],
                    // noderef:null,
                    // path:null,
                    // noderefparent:null,
                    // typefileratache:null,
                    // valider:null ,


                    console.log("data source ===> ", this.dataSourcePj);
                    this.dataSourcePj.push({
                        //id: d,
                        name: 'Autre',
                        pieceafournir: null,
                        nomfichierrattache: null,
                        pjfournie: null,
                        cocher: null,
                        tableaufile: [],
                        typefileratache: null,
                        valider: null,
                        noderef: null,
                        path: null,
                        nodrefparent: null,
                        activite: this.dossierpj['activityname'],
                        dossierPJ: this.dossierpj,
                        comptePJ: this.datadossier.comptePJ,
                        version: null


                    });


                    if (this.dataSourcePj[this.dataSourcePj.length - 1].name == "Autre") {
                        let piecejointe = new pj();
                        piecejointe.pieceafournir = this.dataSourcePj[this.dataSourcePj.length - 1].pieceafournir;
                        piecejointe.path = this.dataSourcePj[this.dataSourcePj.length - 1].path;
                        piecejointe.obligatoire = this.dataSourcePj[this.dataSourcePj.length - 1].obligatoire;
                        piecejointe.cocher = this.dataSourcePj[this.dataSourcePj.length - 1].cocher;
                        piecejointe.nomfichierrattache = this.dataSourcePj[this.dataSourcePj.length - 1].nomfichierrattache;
                        piecejointe.name = this.dataSourcePj[this.dataSourcePj.length - 1].name;
                        piecejointe.noderef = this.dataSourcePj[this.dataSourcePj.length - 1].noderef;
                        piecejointe.typefileratache = this.dataSourcePj[this.dataSourcePj.length - 1].typefileratache;
                        piecejointe.noderefparent = this.dataSourcePj[0].noderefparent;
                        piecejointe.pjfournie = this.dataSourcePj[this.dataSourcePj.length - 1].pjfournie;
                        piecejointe.dossierPJ = this.dataSourcePj[this.dataSourcePj.length - 1].dossierPJ;
                        piecejointe.comptePJ = this.dataSourcePj[this.dataSourcePj.length - 1].comptePJ;
                        piecejointe.valider = this.dataSourcePj[this.dataSourcePj.length - 1].valider;
                        piecejointe.version = this.dataSourcePj[this.dataSourcePj.length - 1].version;

                        this.saveonlyonepiece(piecejointe);
                        //this.getpjsfromdb(this.iddossier) ;
                        this.messageEvent.emit(this.dataSourcePj);
                    }

                }


            }

            if (this.cle === "fromcompte") {

                let include = [];
                for (let i = 0; i < this.dataSourcePj.length; i++) {


                    include[include.length] = this.dataSourcePj[i].name.includes('Autre');
                }

                console.log("tableau include ", include);

                if (include.includes(true)) {
                    console.log("exist");
                    this.menu1 = this.dataSourcePj;
                    let i = 0;
                    let test = false;
                    do {
                        test = true;
                        if ((this.menu1[i].name === "Autre")) {
                            if ((this.menu1[i].nomfichierrattache === "") || (this.menu1[i].nomfichierrattache === null)) {

                                console.log('block g');
                                test = false;
                                this.toster.error("Veuillez entrer la piéce nomée Autre");
                                break;

                            }
                            else {
                                test = true
                            }
                        }
                        i++;
                    } while ((test === true) && (i < this.menu1.length));

                    if (test === true) {
                        var d = this.dataSourcePj[(this.dataSourcePj.length) - 1].id + 1;


                        console.log("iddddddddd", d);


                        this.dataSourcePj.push({
                            name: 'Autre',
                            pieceafournir: null,
                            nomfichierrattache: null,
                            pjfournie: null,
                            cocher: null,
                            tableaufile: [],
                            typefileratache: null,
                            valider: null,
                            noderef: null,
                            path: null,
                            nodrefparent: null,
                            activite: this.comptepj['activityname'],
                            dossierPJ: null,
                            comptePJ: this.comptepj,
                            version: null


                        });

                        //this.messageEvent.emit(this.pieceJoinNom1);


                        // t.noderefparent = dataa.entry.id;

                        if (this.dataSourcePj[this.dataSourcePj.length - 1].name == "Autre") {
                            let piecejointe = new pj();
                            piecejointe.pieceafournir = this.dataSourcePj[this.dataSourcePj.length - 1].pieceafournir;
                            piecejointe.path = this.dataSourcePj[this.dataSourcePj.length - 1].path;
                            piecejointe.obligatoire = this.dataSourcePj[this.dataSourcePj.length - 1].obligatoire;
                            piecejointe.cocher = this.dataSourcePj[this.dataSourcePj.length - 1].cocher;
                            piecejointe.nomfichierrattache = this.dataSourcePj[this.dataSourcePj.length - 1].nomfichierrattache;
                            piecejointe.name = this.dataSourcePj[this.dataSourcePj.length - 1].name;
                            piecejointe.noderef = this.dataSourcePj[this.dataSourcePj.length - 1].noderef;
                            piecejointe.typefileratache = this.dataSourcePj[this.dataSourcePj.length - 1].typefileratache;
                            piecejointe.noderefparent = this.dataSourcePj[0].noderefparent;
                            piecejointe.pjfournie = this.dataSourcePj[this.dataSourcePj.length - 1].pjfournie;
                            piecejointe.dossierPJ = this.dataSourcePj[this.dataSourcePj.length - 1].dossierPJ;
                            piecejointe.comptePJ = this.dataSourcePj[this.dataSourcePj.length - 1].comptePJ;
                            piecejointe.valider = this.dataSourcePj[this.dataSourcePj.length - 1].valider;
                            piecejointe.version = this.dataSourcePj[this.dataSourcePj.length - 1].version;

                            this.saveonlyonepiece(piecejointe);

                            this.messageEvent.emit(this.dataSourcePj);
                        }

                    }
                }

                else {
                    console.log("not exist ", this.dataSourcePj);
                    // var d = new Date();
                    //  console.log(this.pieceJoinNom1[(this.pieceJoinNom1.length) - 1].id + 1);
                    var d = this.dataSourcePj[(this.dataSourcePj.length) - 1].id + 1
                    console.log("iddddddddd", d);


                    // id: i,
                    //     obligatoire:data[i].obligatoire,
                    // pieceafournir:data[i].pieceafournir,
                    // cocher: null,
                    // pjfournie:false ,
                    // name: data[i].nompiece,
                    // NomFile: null,
                    // tableaufile:[],
                    // noderef:null,
                    // path:null,
                    // noderefparent:null,
                    // typefileratache:null,
                    // valider:null ,


                    console.log("data source ===> ", this.dataSourcePj);
                    this.dataSourcePj.push({
                        //id: d,
                        name: 'Autre',
                        pieceafournir: null,
                        nomfichierrattache: null,
                        pjfournie: null,
                        cocher: null,
                        tableaufile: [],
                        typefileratache: null,
                        valider: null,
                        noderef: null,
                        path: null,
                        nodrefparent: null,
                        activite: this.comptepj['activityname'],
                        dossierPJ: null,
                        comptePJ: this.comptepj,
                        version: null


                    });


                    if (this.dataSourcePj[this.dataSourcePj.length - 1].name == "Autre") {
                        let piecejointe = new pj();
                        piecejointe.pieceafournir = this.dataSourcePj[this.dataSourcePj.length - 1].pieceafournir;
                        piecejointe.path = this.dataSourcePj[this.dataSourcePj.length - 1].path;
                        piecejointe.obligatoire = this.dataSourcePj[this.dataSourcePj.length - 1].obligatoire;
                        piecejointe.cocher = this.dataSourcePj[this.dataSourcePj.length - 1].cocher;
                        piecejointe.nomfichierrattache = this.dataSourcePj[this.dataSourcePj.length - 1].nomfichierrattache;
                        piecejointe.name = this.dataSourcePj[this.dataSourcePj.length - 1].name;
                        piecejointe.noderef = this.dataSourcePj[this.dataSourcePj.length - 1].noderef;
                        piecejointe.typefileratache = this.dataSourcePj[this.dataSourcePj.length - 1].typefileratache;
                        piecejointe.noderefparent = this.dataSourcePj[0].noderefparent;
                        piecejointe.pjfournie = this.dataSourcePj[this.dataSourcePj.length - 1].pjfournie;
                        piecejointe.dossierPJ = this.dataSourcePj[this.dataSourcePj.length - 1].dossierPJ;
                        piecejointe.comptePJ = this.dataSourcePj[this.dataSourcePj.length - 1].comptePJ;
                        piecejointe.valider = this.dataSourcePj[this.dataSourcePj.length - 1].valider;
                        piecejointe.version = this.dataSourcePj[this.dataSourcePj.length - 1].version;

                        this.saveonlyonepiece(piecejointe);
                        //this.getpjsfromdb(this.iddossier) ;
                        this.messageEvent.emit(this.dataSourcePj);
                    }

                }


            }


        }

        else{


            this.toastr.error("Veuilez ajouter les fichiers en paramétre");

        }



    }



    addrowold() {
        // var element = document.getElementById('gridList');
        //
        // element.Rows.Add();
        //&& (this.menu1[i].nom === 'Autre')
        this.menu1 = [];
        this.pieceJoinNom1.forEach(item => {
            console.log("name====>", item.name);
            let menu = {
                nom: item.name,
                nomfile: item.NomFile
            };
            console.log('menuuuuuuuu', menu);
            this.menu1.push(menu);
        });
        console.log('menu1', this.menu1);
        let i = 0;
        let test = false;
        do {
            test = true;
            if ((this.menu1[i].nomfile.length === 0)) {
                console.log('block g');
                test = false;
            }
            i++;
        } while ((test === true) && (i < this.menu1.length));
        if (test === true) {
            var d = new Date();
            this.pieceJoinNom1.push({
                id: d.getTime(),
                name: 'Autre',
                NomFile: []
            });
        }
    }

    onToolbarPreparing(e) {
        // visible:this.activitevisibilite,
        e.toolbarOptions.items.unshift({
            visible: true,
            location: 'after',
            widget: 'dxButton',
            options: {
                icon: 'add',
                onClick: this.addrow.bind(this)
            }
        });
    }


    fichesignaletique(data: string | any | Uint8ClampedArray | HTMLDataElement | ArrayBuffer) {
        console.log("aaaaaa")
        this.modalReference = this.modal.open(this.fichesignalique, {size: 'lg'});

    }


}


export class pj {
    id: any;
    obligatoire: boolean;
    cocher: boolean;
    pjfournie: boolean;
    pieceafournir: boolean;
    name: string;
    nomfichierrattache: string;
    noderef: string;
    path: string;
    noderefparent: string;
    dossierPJ: any;
    comptePJ: any;
    typefileratache: string;
    valider: any;
    version: any;

}
