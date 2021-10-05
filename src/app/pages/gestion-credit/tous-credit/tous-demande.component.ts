import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {FlowableService} from "../services-demande/flowable.service";
import {DemandeService} from "../services-demande/servicecredit.service";
import {DxDataGridComponent} from "devextreme-angular/ui/data-grid";
import config from "devextreme/core/config";


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
  selector: 'app-tous-demande',
  templateUrl: './tous-demande.component.html',
  providers:[ DemandeService ],
  styleUrls: ['./tous-demande.component.scss']
})
export class TousDemandeComponent implements OnInit {

  @ViewChild(DxDataGridComponent, {static: true}) dataGrid: DxDataGridComponent;

  selectedRowIndex = -1;

  demandes: any[] = [];

  mode: any = true;

  constructor(private router:Router,private creditService: DemandeService, private flowableService: FlowableService) {
  this.getAllDemandes();
  }

  ngOnInit() {

  }

  addRow() {
    this.router.navigate(['pages/demande']);
  }
    openForm(data) {
        console.log("Demande ::> ",data)
        this.router.navigate(["/pages/demande" + '/' + data.id]);
    }

  onEdit(data) {
    this.router.navigate(['pages/demande/'+data.id]);


  }
  onShowDetails(data) {
    this.router.navigate(['pages/demande/'+data.id]);


  }


  selectedChanged(e, data) {

    this.selectedRowIndex = e.component.getRowIndexByKey(e.selectedRowKeys[0]);

    console.log('==> SELECTED ROW :: ', this.dataGrid.instance.getSelectedRowsData()[0], '==> SELECTED INDEX ::',this.selectedRowIndex);
    // this.creditService.getDemandeAccess(localStorage.getItem('login'),data.id).subscribe((data:any)=> {
    //   if (data === true) {
    //     this.mode = false;
    //   } else {
    //     this.mode = true;
    //   }
    // });

  }

  getAllDemandes(){

    this.creditService.getMesDemandesList(sessionStorage.getItem('username'), sessionStorage.getItem('roles'), sessionStorage.getItem('profileUser')).subscribe((data:any[]) => {

      this.demandes = data;
      console.log('All DEMANDES **** ', this.demandes);
      return data;
    });
  }


}
