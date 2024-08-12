import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { RequestedServiceComponent } from './requested-service/requested-service.component';
import { RequestedServiceRoutingModule } from './requested-service-routing.module';


@NgModule({
  imports: [
    CommonModule,
    RequestedServiceComponent,
    RequestedServiceRoutingModule
  ]
})

export class RequestedServiceModule { }
