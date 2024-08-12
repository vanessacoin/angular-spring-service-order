import { CommonModule, JsonPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCard, MatCardContent, MatCardModule } from '@angular/material/card';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerInputEvent, MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormField, MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { MatIcon, MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';

import {
  RequestedService,
  RequestedServiceComponent,
} from '../../requested-service/requested-service/requested-service.component';
import { SequentialComponent } from '../../sequential/sequential.component';
import { Customer } from './../../customer/model/customer';
import { CustomerService } from './../../customer/services/customer.service';
import { Vehicle } from './../../vehicle/model/vehicle';
import { VehicleService } from './../../vehicle/services/vehicle.service';
import { UsedItem, UsedItemsComponent } from '../../used-items/used-items/used-items.component';


@Component({
  selector: 'app-order',
  standalone: true,
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss',
  providers: [provideNativeDateAdapter(), CustomerService, VehicleService],
  imports: [MatFormField,
    SequentialComponent,
    CommonModule,
    MatFormFieldModule,
    MatDatepickerModule,
    FormsModule,
    JsonPipe,
    MatSelectModule,
    RequestedServiceComponent,
    ReactiveFormsModule,
    MatButtonModule,
    MatCard, MatCardContent, MatCardModule,
    MatLabel,
    MatIcon,
    MatIconModule,
    MatInputModule,
    MatTableModule,
    MatTabsModule,
    UsedItemsComponent
  ]
})
export class OrderComponent implements OnInit {
  orderDate = new Date();
  customers: Customer[] = [];
  vehicles: Vehicle[] = [];
  filteredVehicles: Vehicle[] = [];
  selectedCustomer: Customer | undefined;
  selectedVehicle: Vehicle | undefined;

  requestedService: RequestedService[] = [];
  idRequestedServiceCounter: number = 1;

  usedItems: UsedItem[] = [];
  idItemCounter: number = 1;


  constructor(private customerService: CustomerService, private vehicleService: VehicleService) {}

  ngOnInit(): void {
    this.orderDate = new Date();

    this.customerService.list().subscribe((data: Customer[]) => {
      this.customers = data;
    });

    this.vehicleService.list().subscribe((data: Vehicle[]) => {
      this.vehicles = data;
      this.filteredVehicles = [];
    });
  }

  onDateChange(event: MatDatepickerInputEvent<Date>): void {
    this.orderDate = event.value ?? new Date();
  }

  onCustomerChange(): void {
    if (this.selectedCustomer) {
      this.filteredVehicles = this.vehicles.filter(vehicle => vehicle.id_customer === this.selectedCustomer!._id);
    } else {
      this.filteredVehicles = [];
    }
    this.selectedVehicle = undefined; // Reset selected vehicle when customer changes
  }

}
