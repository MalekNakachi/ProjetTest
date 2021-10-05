import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {IMultiSelectOption, IMultiSelectSettings, IMultiSelectTexts} from 'angular-2-dropdown-multiselect';
import {GestionUtilisateursService} from './gestion-utilisateurs.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-gestion-utilisateurs',
  templateUrl: './gestion-utilisateurs.component.html',
  styleUrls: ['./gestion-utilisateurs.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class GestionUtilisateursComponent  implements OnInit {


  public modalRef: NgbModalRef;
  public user: any;
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

  constructor(public fb: FormBuilder, public modalService: NgbModal, private gestionUtilisateurService: GestionUtilisateursService, public toastrService: ToastrService) {

    this.gestionUtilisateurService.get_all_users().subscribe((users: any[]) => {
      this.users = users;
      this.gestionUtilisateurService.get_all_roles().subscribe((roles: any[]) => {
        this.roles = roles;
      });
    });


  }


  ngOnInit() {
    this.form = this.fb.group({
      id: null,
      email: [null, Validators.compose([Validators.required, Validators.minLength(5)])],
      login: [null, Validators.compose([Validators.required, Validators.minLength(5)])],
      description: [null, Validators.compose([Validators.required])],
      dateupdate: [new Date()],
      modifiedby: ['adminCERT', Validators.compose([Validators.required])],
      password: [null, Validators.compose([Validators.required, Validators.minLength(6)])],
      datecreate: [new Date()],
      role: [null, Validators.compose([Validators.required])],
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
      email: [null, Validators.compose([Validators.required, Validators.minLength(5)])],
      login: [null, Validators.compose([Validators.required, Validators.minLength(5)])],
      description: [null, Validators.compose([Validators.required])],
      dateupdate: [new Date()],
      modifiedby: ['adminCERT', Validators.compose([Validators.required])],
      password: [null, Validators.compose([Validators.required, Validators.minLength(6)])],
      datecreate: [new Date()],
      role: [null, Validators.compose([Validators.required])],
    })
    this.modalRef = this.modalService.open(modalContent, { container: '.app' , backdrop: 'static', keyboard: false});

  }
  public openModalEdit(modalContentEdit, user ) {
    console.log(user)
    let roleId = 2;
    for(let role of this.roles){
      if(role['name'] === user.role){
        roleId = role['id'];
      }
    }

    this.form = this.fb.group({
      id: user.id,
      email: [user.email, Validators.compose([Validators.required, Validators.minLength(5)])],
      login: [user.login, Validators.compose([Validators.required, Validators.minLength(5)])],
      description: [user.description, Validators.compose([Validators.required])],
      dateupdate: [new Date()],
      modifiedby: ['adminCERT', Validators.compose([Validators.required])],
      password: [user.password, Validators.compose([Validators.required, Validators.minLength(6)])],
      datecreate: [user.datecreate],
      role: [user.role.id, Validators.compose([Validators.required])],
    })

    this.modalRef = this.modalService.open(modalContentEdit, { container: '.app' , backdrop: 'static', keyboard: false});
    this.user = user

  }


  public toggle(type){
    this.type = type;
  }
  public closeModal(){
    this.modalRef.close();
  }

  public onSubmit(user):void {
    // console.log(user)
    // let roleId = user.role
    //
    this.roles.forEach(elem => {
      if (user.role = elem['id']) {
        user.role = elem;
          this.gestionUtilisateurService.create_user(user).subscribe((res: any[]) => {
            this.toastrService.success('Utilisateur ajouter avec succée!', 'Operation Succées!');
            this.gestionUtilisateurService.get_all_users().subscribe((data: any[]) => {
              this.users = data;
            });
          }, error1 => {
            this.toastrService.error('Un erreur est survenu, veuillez contacté votre administrateur!', 'Operation Echouée!');
          });

          console.log('user => ', user);
      }
    });
    // user['role'] = 'administrateur'
    // user['id'] = this.users.length
  }
  public onSubmitEdit(user):void {
    this.roles.forEach(elem => {
      if (user.role = elem['id']) {
        user.role = elem;
        this.gestionUtilisateurService.update_user(user).subscribe((res: any[]) => {
          this.toastrService.success('Utilisateur modifié avec succée!', 'Operation Succées!');
          this.gestionUtilisateurService.get_all_users().subscribe((data: any[]) => {
            this.users = data;
          });
        }, error1 => {
          this.toastrService.error('Un erreur est survenu, veuillez contacté votre administrateur!', 'Operation Echouée!');
        });
        console.log('user => ', user);
      }
    });
  }

  deleteUser(user){
    this.gestionUtilisateurService.delete_user(user).subscribe((res: any[]) => {
      this.toastrService.success('Utilisateur Supprimé avec succée!', 'Operation Succées!');
      this.gestionUtilisateurService.get_all_users().subscribe((data: any[]) => {
        this.users = data;
      });
    }, error1 => {
      this.toastrService.error('Un erreur est survenu, veuillez contacté votre administrateur!', 'Operation Echouée!');
    });

    console.log('user => ', user);
  }
}


