import { Component, OnInit } from '@angular/core';
import {environment} from "../../../environments/environment.prod";
import {ModalFileModuleComponent} from "../../fileModule/Modal_Scan-profil/modalFileModule.component";
import {Service} from "../../fileModule/alfresco_services/document-library.service";
import {NgbModal, NgbModalRef} from "@ng-bootstrap/ng-bootstrap";
import {ServiceFileService} from "../../fileModule/alfresco_services/service-file.service";
import {EnvService} from "../../../env.service";
import {PjService} from "../pj.service";
import {HttpParams} from "@angular/common/http";
import * as FileSaver from 'file-saver';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  profileManager:any;
  public thumbnailphoto;
  username: string;
  profiles: string;
  public profile;
  public roles;
  public nomuser;

  userprofile= {
    login: "",
    nomscanner: "",
    mode: "",
    rectoverso: "",
    resolution: "",
    typedudocument:"",
    tailledufichier:""
  }


  listeScanner:any[]=[];
  public modalRef: NgbModalRef;


  constructor( private service: Service, private modalService: NgbModal,private servicefile : ServiceFileService,private  pj :PjService,private env :EnvService) {
    this.thumbnailphoto=localStorage.getItem("thumbnailphoto")
    this.nomuser=sessionStorage.getItem("profileUser")
    this.profileManager=this.env.apiUrlprofile
    this.profiles=sessionStorage.getItem("profiles");


  }

  ngOnInit() {
  }



  openmodaluserprofilescanner() {
    // console.log(e.entry);


    this.service.GetListScanner('GetListScanner').then(res => {
      console.log("getlistscanner innnnnn");

      console.log("res", res);


      for (let i = 0; i < res.result.length; i++) {
        console.log("in");
        this.listeScanner[i] = res.result[i];
      }

      this.servicefile.getuserprofile(sessionStorage.getItem("datalogin")).then(

          datauserprofile => {

            console.log(datauserprofile);

/// le if du null ici
// test if profile exist
            if (datauserprofile != null) {

              this.userprofile=datauserprofile ;
              //  document.getElementById('tt').click();
              this.modalRef = this.modalService.open(ModalFileModuleComponent, {size: 'lg'});
              this.modalRef.componentInstance.datauserprofile=this.userprofile ;
              this.modalRef.componentInstance.dataList=this.listeScanner
            }

            else {
              this.modalRef = this.modalService.open(ModalFileModuleComponent, {size: 'lg'});

              //
              // this.modalRef.componentInstance.data=this.parentFixId;
              console.log(this.modalRef.componentInstance.data);
              this.modalRef.componentInstance.dataList=this.listeScanner;
            }

          }
      )


    });
  }



  /*********************************download toolkit********************************************/
  downloadtoolkit() {

    let noderef = 'workspace://SpacesStore/'+this.env.nodeRefToolkit;
    console.log("nodeRef",noderef)
    const params = new HttpParams().set('noderefFile', noderef);
    this.pj.downloadfileCMIS(params).subscribe((data: any) => {
      let aux = data.headers.get('Content-Disposition').substring(data.headers.get('Content-Disposition').lastIndexOf('=') + 1, data.headers.get('Content-Disposition').length);

      var blob = new Blob([data.body]);

      FileSaver.saveAs(blob, aux);

    });
  }


}
