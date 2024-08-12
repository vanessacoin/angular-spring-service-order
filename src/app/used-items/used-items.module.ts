import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsedItemsComponent } from './used-items/used-items.component';
import { UsedItemsRoutingModule } from './used-items-routing.module';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    UsedItemsRoutingModule,
    UsedItemsComponent
  ]
})
export class UsedItemsModule { }
