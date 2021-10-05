
import { NgModule } from '@angular/core';
import {DxDataGridModule} from 'devextreme-angular/ui/data-grid'
import {PerfectScrollbarModule} from 'ngx-perfect-scrollbar';
import {MultiselectDropdownModule} from "angular-2-dropdown-multiselect";
import {DxSpeedDialActionModule} from "devextreme-angular/ui/speed-dial-action";
import {DxDropDownBoxModule} from "devextreme-angular/ui/drop-down-box";
import {DxTreeViewModule} from "devextreme-angular/ui/tree-view";
import {DxSelectBoxModule} from "devextreme-angular/ui/select-box";
import {DxCheckBoxModule} from "devextreme-angular/ui/check-box";
import {DxListModule} from "devextreme-angular/ui/list";
import {DxTagBoxModule} from "devextreme-angular/ui/tag-box";
import {DxTemplateModule} from "devextreme-angular/core/template";
import {DxDropDownButtonModule} from "devextreme-angular/ui/drop-down-button";
import {DxToolbarModule} from "devextreme-angular/ui/toolbar";
import {DxButtonModule} from "devextreme-angular/ui/button";
import {DxFormModule} from "devextreme-angular/ui/form";
import {MultiSelectModule} from "@syncfusion/ej2-angular-dropdowns";
import {DxDateBoxModule} from "devextreme-angular/ui/date-box";
import { DxPopupModule } from "devextreme-angular/ui/popup";
@NgModule({
    declarations: [
    ],
    imports: [


    ],
    exports: [
        PerfectScrollbarModule,

        DxSpeedDialActionModule,
        DxDataGridModule,
        DxDropDownBoxModule,
        DxTreeViewModule,
        DxSelectBoxModule,
        DxCheckBoxModule,
        DxListModule,
        DxTagBoxModule,
        DxTemplateModule,
        DxDropDownButtonModule,
        DxToolbarModule,
        DxButtonModule,
        DxFormModule,
        MultiSelectModule,
        DxDateBoxModule,
        MultiselectDropdownModule,
        DxPopupModule,
    ],
    providers: [

    ]
})
export class SharedDevExtremeModuleModule {

}
