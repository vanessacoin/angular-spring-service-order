import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { VehicleFormComponent } from './container/vehicle-form/vehicle-form.component';
import { VehicleComponent } from './container/vehicle/vehicle.component';

const routes: Routes = [
  { path: '', component: VehicleComponent},
  { path: 'new', component: VehicleFormComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VehicleRoutingModule { }
