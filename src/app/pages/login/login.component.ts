import { Component, ViewEncapsulation } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, AbstractControl, FormBuilder, Validators} from '@angular/forms';
import { CustomValidators } from 'ng2-validation';
import {AuthService} from "../../auth.service";
import {HttpClient, HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [AuthService],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent {
  public router: Router;
  public form:FormGroup;
  public login:AbstractControl;
  public password:AbstractControl;
  infWrong: any = false;
  infNotWrong: any = false;
  loading: any = false;

  constructor(router:Router, fb:FormBuilder, private authService: AuthService) {
    this.router = router;
    this.form = fb.group({
      'login': ['', Validators.compose([Validators.required])],
      'password': ['', Validators.compose([Validators.required, Validators.minLength(6)])]
    });

    this.login = this.form.controls['login'];
    this.password = this.form.controls['password'];
  }



  ngAfterViewInit(){
    document.getElementById('preloader').classList.add('hide');
  }

  public onSubmit_old(values: SignInModel): void {
    this.infWrong = false;
    this.loading = true;
    this.infNotWrong = false;
    console.log(values.login, values.password)
    this.authService.authentifyProfile(values.login, values.password).then(
      data => {
        this.loading = false;
        this.infNotWrong = true;
        localStorage.setItem('login', values.login);

      },
      error => {
        console.log('error');
        this.infWrong = true;
        this.loading = false;
      }
    );


  }

  public onSubmit(values: SignInModel): void {
    console.log(values["login"])
    console.log(values["password"]);
    let samaacountName = values["login"];
    this.authService.getToken(values.login, values.password).then(
        (response:any) => {

          let token = response.body;
          console.log(token)
          if (token != "" && token != null && token != undefined) {

            sessionStorage.setItem("username", samaacountName);
            sessionStorage.setItem("token", token);
            sessionStorage.setItem("datalogin", samaacountName)
            this.authService.getMy(token).subscribe(dataofMy => {
              sessionStorage.setItem("profileUser", dataofMy['data']['displayname']);
              sessionStorage.setItem("UserName", samaacountName)
              if(samaacountName!="root"){
                this.authService.getRoleUser(samaacountName, token).subscribe(data => {

                  sessionStorage.setItem('roles', data['roles']);
                  sessionStorage.setItem("profiles", data['profiles'])



                  if ((data['profiles'] === "MMAdmin")) {

                    this.router.navigate(['pages/dashboard'])

                  } else {

                    this.router.navigate(['pages/dashboard'])

                  }



                });
              }




            })


          }
          else {

          }
        },
        err => {
          // this.toster.error("login ou password incorrect")
          console.log("error", err)
          if (err.status == 401) {
            this.router.navigate([''])
            // this.currentToken = "error";
          }
        }
      )

  }


}

export class SignInModel {
  login: string;
  password: string;

}

