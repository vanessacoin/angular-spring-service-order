import { CommonModule, JsonPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerInputEvent, MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormField, MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTabsModule } from '@angular/material/tabs';

import { SequentialComponent } from '../../sequential/sequential.component';
import { Customer } from './../../customer/model/customer';
import { CustomerService } from './../../customer/services/customer.service';
import { Vehicle } from './../../vehicle/model/vehicle';
import { VehicleService } from './../../vehicle/services/vehicle.service';

@Component({
  selector: 'app-order',
  standalone: true,
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss',
  providers: [provideNativeDateAdapter(), CustomerService, VehicleService],
  imports: [MatFormField,
    SequentialComponent,
    MatLabel,
    CommonModule,
    MatTabsModule,
    MatInputModule,
    MatFormFieldModule,
    MatDatepickerModule,
    FormsModule,
    ReactiveFormsModule,
    JsonPipe,
    MatSelectModule
  ]
})
export class OrderComponent implements OnInit {
  orderDate = new Date;
  customers: Customer[] = [];
  vehicles: Vehicle[] = [];
  filteredVehicles: Vehicle[] = [];
  selectedCustomer: Customer | undefined;
  selectedVehicle: Vehicle | undefined;

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
