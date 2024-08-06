import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RequestedServiceComponent } from './requested-service/requested-service.component';

const routes: Routes = [
  { path: '', component: RequestedServiceComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class RequestedServiceRoutingModule { }
