import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {IMultiSelectOption, IMultiSelectSettings, IMultiSelectTexts} from 'angular-2-dropdown-multiselect';
import {GestionRolesService} from './gestion-roles.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-gestion-roles',
  templateUrl: './gestion-roles.component.html',
  styleUrls: ['./gestion-roles.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class GestionRolesComponent  implements OnInit {


  public modalRef: NgbModalRef;
  public role: any;
  public searchText: string;
  public users: any[] = [];
  public form: FormGroup;
  public p: any = 1;
  public type: any = 'grid';
  public roles: any[] = [];
  public menuSelectSettings: IMultiSelectSettings = {
    enableSearch: true,
    checkedStyle: 'fontawesome',
    buttonClasses: 'btn btn-secondary btn-block',
    dynamicTitleMaxItems: 0,
    displayAllSelectedText: true,
    showCheckAll: true,
    showUncheckAll: true
  };

  public menuSelectTexts: IMultiSelectTexts = {
    checkAll: 'Select all',
    uncheckAll: 'Unselect all',
    checked: 'menu item selected',
    checkedPlural: 'menu items selected',
    searchPlaceholder: 'Find menu item...',
    defaultTitle: 'Select menu items for user',
    allSelected: 'All selected',
  };
  public menuSelectOptions: IMultiSelectOption[] = [];

  constructor(public fb: FormBuilder, public modalService: NgbModal, private gestionUtilisateurService: GestionRolesService, public toastrService: ToastrService) {

      this.gestionUtilisateurService.get_all_roles().subscribe((roles: any[]) => {
        this.roles = roles;
      });



  }


  ngOnInit() {
    this.form = this.fb.group({
      id: null,
      name: [null, Validators.compose([Validators.required, Validators.minLength(5)])],
    })
  }
  activateUser(user){
    user.status = 'actif';
    let roleId = 2;
    for(let role of this.roles){
      if(role['name'] === user.role){
        roleId = role['id'];
        user.role = null;
      }
    }
    // this.gestionUtilisateurService.createUtilisateur(user, roleId).subscribe((res: any[]) => {
    //   console.log('succee')
    //   this.gestionUtilisateurService.getAllUsers().subscribe((data: any[]) => {
    //     this.users = data;
    //   });
    // });
  }

  desactivateUser(user){
    user.status = 'inactif';
    let roleId = 2;
    for(let role of this.roles){
      if(role['name'] === user.role){
        roleId = role['id'];
        user.role = null;
      }
    }
    // this.gestionUtilisateurService.createUtilisateur(user, roleId).subscribe((res: any[]) => {
    //   console.log('succee')
    //   this.gestionUtilisateurService.getAllUsers().subscribe((data: any[]) => {
    //     this.users = data;
    //   });
    // });
  }
  public openMenuAssign(event){
    let parent = event.target.parentNode;
    while (parent){
      parent = parent.parentNode;
      if(parent.classList.contains('content')){
        parent.classList.add('flipped');
        parent.parentNode.parentNode.classList.add('z-index-1');
        break;
      }
    }
  }

  public closeMenuAssign(event){
    let parent = event.target.parentNode;
    while (parent){
      parent = parent.parentNode;
      if(parent.classList.contains('content')){
        parent.classList.remove('flipped');
        parent.parentNode.parentNode.classList.remove('z-index-1');
        break;
      }
    }
  }

  public assignMenuItemsToUser(user){

  }

  public openModal(modalContent, user ) {
    this.form = this.fb.group({
      id: null,
      name: null
    })
    this.modalRef = this.modalService.open(modalContent, { container: '.app' , backdrop: 'static', keyboard: false});

  }
  public openModalEdit(modalContentEdit, role ) {
    console.log(role)

    this.form = this.fb.group({
      id: role.id,
      name: [role.name, Validators.compose([Validators.required, Validators.minLength(5)])],
    })

    this.modalRef = this.modalService.open(modalContentEdit, { container: '.app' , backdrop: 'static', keyboard: false});
    this.role = role

  }


  public toggle(type){
    this.type = type;
  }
  public closeModal(){
    this.modalRef.close();
  }

  public onSubmit(role):void {
    // console.log(user)
    // let roleId = user.role
    //
          this.gestionUtilisateurService.create_role(role).subscribe((res: any[]) => {
            this.toastrService.success('Role ajouter avec succée!', 'Operation Succées!');
            this.gestionUtilisateurService.get_all_roles().subscribe((data: any[]) => {
              this.roles = data;
            });
          }, error1 => {
            this.toastrService.error('Un erreur est survenu, veuillez contacté votre administrateur!', 'Operation Echouée!');
          });

          console.log('role => ', role);
    // user['role'] = 'administrateur'
    // user['id'] = this.users.length
  }
  public onSubmitEdit(role):void {
    this.roles.forEach(elem => {
      if (role.role = elem['id']) {
        role.role = elem;
        this.gestionUtilisateurService.update_role(role).subscribe((res: any[]) => {
          this.toastrService.success('Role modifié avec succée!', 'Operation Succées!');
          this.gestionUtilisateurService.get_all_roles().subscribe((data: any[]) => {
            this.roles = data;
          });
        }, error1 => {
          this.toastrService.error('Un erreur est survenu, veuillez contacté votre administrateur!', 'Operation Echouée!');
        });
        console.log('role => ', role);
      }
    });
  }

  deleteUser(role){
    this.gestionUtilisateurService.delete_role(role).subscribe((res: any[]) => {
      this.toastrService.success('Role Supprimé avec succée!', 'Operation Succées!');
      this.gestionUtilisateurService.get_all_roles().subscribe((data: any[]) => {
        this.roles = data;
      });
    }, error1 => {
      this.toastrService.error('Un erreur est survenu, veuillez contacté votre administrateur!', 'Operation Echouée!');
    });

    console.log('role => ', role);
  }
}


