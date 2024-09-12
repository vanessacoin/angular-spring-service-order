import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { CustomerComponent } from './container/customer/customer.component';
import { CustomerFormComponent } from './container/customer-form/customer-form.component';

const routes: Routes = [
  { path: '', component: CustomerComponent},
  { path: 'new', component: CustomerFormComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
