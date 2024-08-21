import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UsedItemsComponent } from './used-items/used-items.component';

const routes: Routes = [
  { path: '', component: UsedItemsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsedItemsRoutingModule { }
