import { AsyncPipe, NgIf } from '@angular/common';
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

import { Vehicle } from '../model/vehicle';
import { VehicleService } from '../services/vehicle.service';

@Component({
  selector: 'app-vehicle',
  standalone: true,
  imports: [MatTabsModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule,
    HttpClientModule,
    MatCardModule,
    NgIf,
    AsyncPipe,
    MatProgressSpinnerModule],
  templateUrl: './vehicle.component.html',
  styleUrl: './vehicle.component.scss',
  providers: [VehicleService]
})

export class VehicleComponent implements OnInit {
  @Input() vehicle_list: Vehicle[] = [];
  @Output() add = new EventEmitter(false);
  @Output() edit = new EventEmitter(false);
  @Output() remove = new EventEmitter(false);

  vehicles$: Observable<Vehicle[]>;
  displayedColumns = ['brand', 'model', 'plate', 'year', 'color', 'id_customer', 'actions']

  constructor(private vehicleService: VehicleService) {
    this.vehicles$ = this.vehicleService.list();
   }

  ngOnInit(): void {
    this.vehicles$ = this.vehicleService.list();
  }

  onAdd() {
    this.add.emit(true);
  }

  onEdit(vehicle: Vehicle) {
    this.edit.emit(vehicle);
  }

  onDelete(vehicle: Vehicle) {
    this.remove.emit(vehicle);
  }
}



