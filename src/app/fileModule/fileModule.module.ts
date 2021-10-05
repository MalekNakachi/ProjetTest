import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FileuploadalfrescoComponent} from "./uploadFile/fileuploadalfresco.component";
import {PdfViewerModule} from "ng2-pdf-viewer";

import {ModalFileModuleComponent} from "./Modal_Scan-profil/modalFileModule.component";

import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SharedDevExtremeModuleModule} from "../shared-dev-extreme/sharedDevExtreme.module";
import {DocViewerComponent} from "./docViewer.component";
import {NgxDocViewerModule} from "ngx-doc-viewer";
import {DxLoadPanelModule} from "devextreme-angular";




@NgModule({
    declarations: [
        DocViewerComponent,
        FileuploadalfrescoComponent,
        ModalFileModuleComponent,


    ],
    imports: [

        CommonModule,
        PdfViewerModule,
        FormsModule,
        ReactiveFormsModule,
        SharedDevExtremeModuleModule,
        NgxDocViewerModule,
        DxLoadPanelModule
    ],
    exports: [

        FileuploadalfrescoComponent,
        DocViewerComponent,


    ],
    providers: [

    ]
})
export class fileModuleModule {

}
