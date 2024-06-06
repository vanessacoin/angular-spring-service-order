import { CommonModule, JsonPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatDatepickerInputEvent, MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormField, MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTabsModule } from '@angular/material/tabs';

import { SequentialComponent } from '../../sequential/sequential.component';


@Component({
  selector: 'app-order',
  standalone: true,
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss',
  providers: [provideNativeDateAdapter()],
  imports: [MatFormField, SequentialComponent, MatLabel, CommonModule, MatTabsModule, MatInputModule,
    MatFormFieldModule, MatDatepickerModule, FormsModule, ReactiveFormsModule, JsonPipe,
  ]
})
export class OrderComponent implements OnInit {
  orderDate = new Date;

  constructor() { }

  ngOnInit(): void {
    this.orderDate = new Date();
  }

  onDateChange(event: MatDatepickerInputEvent<Date>): void {
    this.orderDate = event.value ?? new Date();
  }
}
