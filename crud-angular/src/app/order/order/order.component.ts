import { CommonModule, JsonPipe } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit, Output, EventEmitter } from '@angular/core';
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
import { UsedItem, UsedItemsComponent } from '../../used-items/used-items/used-items.component';
import { Order } from '../model/order.model';
import { Customer } from './../../customer/model/customer';
import { CustomerService } from './../../customer/services/customer.service';
import { Vehicle } from './../../vehicle/model/vehicle';
import { VehicleService } from './../../vehicle/services/vehicle.service';

import { OrderService } from '../services/order.service';


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
  selectedCustomer: Customer | undefined;

  vehicles: Vehicle[] = [];
  selectedVehicle: Vehicle | undefined;
  filteredVehicles: Vehicle[] = [];

  requestedService: RequestedService[] = [];
  idRequestedServiceCounter: number = 1;

  usedItems: UsedItem[] = [];
  idItemCounter: number = 1;

  laborCost: number = 0;
  totalCost: number = 0;
  totalItems: number = 0;
  totalOrder: number = 0;
  totalUsedItems: number = 0;

  order: Order = {
    id: 0,
    orderDate: new Date(),
    customerId: '',
    customerName: '',
    customerCpf: '',
    customerPhone: '',
    customerEmail: '',
    vehicleId: '',
    vehicleBrand: '',
    vehicleModel: '',
    vehiclePlate: '',
    vehicleKm: 0,
    vehicleYear: 0,
    vehicleColor: '',
    vehicleCustomerId: '',
    requestedServices: [],
    usedItems: [],
    laborCost: 0,
    totalOrder: 0
  }

  constructor(
    private readonly customerService: CustomerService,
    private readonly vehicleService: VehicleService,
    private readonly http: HttpClient,
    private readonly orderService: OrderService
  ) {

  }

  ngOnInit(): void {
    this.orderDate = new Date();

    this.customerService.list().subscribe((data: Customer[]) => {
      this.customers = data;
    });

    this.vehicleService.list().subscribe((data: Vehicle[]) => {
      this.vehicles = data;
      this.filteredVehicles = [];
    });

    this.updateTotalItems();
    this.updateTotalOrder();
  }

  recalcTotal() {
  const mo = Number(this.laborCost) || 0;
  const itens = Number(this.totalUsedItems) || 0;
  this.totalOrder = mo + itens;
}

  updateTotalCost(totalUsedItems: number): void {
    this.totalUsedItems = totalUsedItems;
    this.updateTotalOrder();
  }

  onLaborCostChange(event: any): void {
    this.laborCost = +event.target.value || 0;
    this.updateTotalOrder();
  }

  updateTotalItems(): void {
    this.totalItems = this.usedItems.reduce((sum, item) => sum + item.amount, 0);
    this.updateTotalOrder();
  }

  updateTotalOrder(): void {
    this.totalOrder = this.totalUsedItems + this.laborCost;
  }

  onUsedItemsChange(updatedUsedItems: UsedItem[]): void {
    this.usedItems = updatedUsedItems;
    this.updateTotalOrder();
  }

  submitOrder() {
    if (!this.selectedCustomer || !this.selectedVehicle) {
      console.error("Cliente e/ou veículo não selecionado(s)!");
      return;
    }

    this.order.vehicleKm = this.order.vehicleKm || 0;

    this.order = {
      ...this.order,
      orderDate: this.orderDate,
      customerId: this.selectedCustomer.id.toString(),
      customerName: this.selectedCustomer.name,
      customerCpf: this.selectedCustomer.cpf,
      customerPhone: this.selectedCustomer.phone,
      customerEmail: this.selectedCustomer.email,
      vehicleId: this.selectedVehicle.id.toString(),
      vehicleBrand: this.selectedVehicle.brand,
      vehicleModel: this.selectedVehicle.model,
      vehiclePlate: this.selectedVehicle.plate,
      vehicleYear: this.selectedVehicle.year,
      vehicleColor: this.selectedVehicle.color,
      vehicleCustomerId: this.selectedVehicle.customer?.id.toString() || '',
      requestedServices: this.requestedService,
      usedItems: this.usedItems,
      laborCost: this.laborCost,
      totalOrder: this.totalOrder
    };

    console.log("Valor do KM antes de enviar:", this.order.vehicleKm);
    console.log("Dados enviados para API:", this.order);

    this.orderService.saveOrder(this.order).subscribe(
      response => {
        console.log('Ordem salva', response);
        this.generateOrderPdf(response.id);
      },
      error => {
        console.error('Erro ao salvar ordem:', error);
      }
    );
  }

  generateOrderPdf(orderId: number): void {
    const url = `/api/orders/${orderId}/pdf`;
    this.http.get(url, { responseType: 'blob' }).subscribe((pdf) => {
      const blob = new Blob([pdf], { type: 'application/pdf' });
      const downloadURL = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = downloadURL;
      link.download = `order_${orderId}.pdf`;
      link.click();

      window.open(`/api/orders/${orderId}/pdf`, '_blank');
    });
  }

  onDateChange(event: MatDatepickerInputEvent<Date>): void {
    this.orderDate = event.value ?? new Date();
  }

  onCustomerChange(): void {
    if (this.selectedCustomer) {
      this.filteredVehicles = this.vehicles.filter(vehicle => vehicle.customer.id === this.selectedCustomer!.id);
    } else {
      this.filteredVehicles = [];
    }
    this.selectedVehicle = undefined;
  }
}
