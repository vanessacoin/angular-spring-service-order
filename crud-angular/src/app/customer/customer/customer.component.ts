import { NgIf } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';

import { Customer } from '../model/customer';
import { CustomerService } from '../services/customer.service';

@Component({
  selector: 'app-customer',
  standalone: true,
  imports: [MatTabsModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule,
    HttpClientModule,
    MatCardModule,
    MatProgressSpinnerModule,
    NgIf,
    AsyncPipe],
  templateUrl: './customer.component.html',
  styleUrl: './customer.component.scss',
  providers: [CustomerService]
})

export class CustomerComponent implements OnInit {
  @Input() customer_list: Customer[] = [];
  @Output() add = new EventEmitter(false);
  @Output() edit = new EventEmitter(false);
  @Output() remove = new EventEmitter(false);

  customers$: Observable<Customer[]>;
  displayedColumns = ['name', 'cpf', 'phone', 'email', 'actions']

  constructor(private customerService: CustomerService) {
    this.customers$ = this.customerService.list();
  }

  ngOnInit(): void { }

  onAdd() {
    this.add.emit(true);
  }

  onEdit(customer: Customer) {
    this.edit.emit(customer);
  }

  onDelete(customer: Customer) {
    this.remove.emit(customer);
  }
}
