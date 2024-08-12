import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MAT_DATE_LOCALE } from '@angular/material/core';

import { RequestedServiceModule } from '../requested-service/requested-service.module';
import { RequestedServiceComponent } from '../requested-service/requested-service/requested-service.component';
import { UsedItemsModule } from '../used-items/used-items.module';
import { UsedItemsComponent } from '../used-items/used-items/used-items.component';
import { OrderRoutingModule } from './order-routing.module';
import { OrderComponent } from './order/order.component';


@NgModule({
  imports: [
    CommonModule,
    OrderComponent,
    OrderRoutingModule,
    RequestedServiceComponent,
    RequestedServiceModule,
    FormsModule,
    ReactiveFormsModule,
    UsedItemsComponent,
    UsedItemsModule
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'pt-BR' }
  ]
})

export class OrderModule { }
