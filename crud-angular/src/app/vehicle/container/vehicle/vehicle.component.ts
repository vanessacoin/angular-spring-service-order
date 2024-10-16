import { AsyncPipe, NgIf } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Observable } from 'rxjs';

import { VehicleService } from '../../services/vehicle.service';
import { Vehicle } from './../../model/vehicle';

;
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
    MatProgressSpinnerModule,
    RouterLink],
  templateUrl: './vehicle.component.html',
  styleUrl: './vehicle.component.scss',
  providers: [VehicleService]
})

export class VehicleComponent implements OnInit {
  @Input() vehicle_list: Vehicle[] = [];
  @Output() add = new EventEmitter(false);
  @Output() edit = new EventEmitter(false);
  @Output() remove = new EventEmitter(false);

  vehiclesDataSource: MatTableDataSource<Vehicle> = new MatTableDataSource<Vehicle>([]);

  vehicles$: Observable<Vehicle[]>;
  displayedColumns: string[] = ['brand', 'model', 'plate', 'year', 'color', 'id_customer', 'actions']

  constructor(
    private readonly service: VehicleService,
    private readonly router: Router,
    private readonly route: ActivatedRoute,
    private readonly snackBar: MatSnackBar
  ) {
    this.vehicles$ = this.service.list();
   }

  ngOnInit(): void {
    this.vehicles$.subscribe(vehicles => {
      this.vehiclesDataSource.data = vehicles;
    })

    this.route.queryParams.subscribe(params => {
      const message = params['message'];

      if (message) {
        this.snackBar.open(message, '', { duration: 3000 });
      }
    });

  }

  onAdd() {
    this.router.navigate(['new'], { relativeTo: this.route });
  }

  onUpdate(vehicle: Vehicle) {
    this.router.navigate(['new'], { relativeTo: this.route,
      queryParams: {
        id: vehicle.id,
        brand: vehicle.brand,
        model: vehicle.model,
        plate: vehicle.plate,
        year: vehicle.year,
        color: vehicle.color,
        id_customer: vehicle.id_customer
      }
     });
  }

  onDelete(vehicle: Vehicle) {
    if(confirm('Deletar veículo ' + vehicle.model + vehicle.brand + vehicle.color + '?')){
      this.service.deleteVehicle(vehicle.id)
          .subscribe({
            next: () => {
              this.snackBar.open('Cliente deletado com sucesso!', '', { duration: 3000 });
              const filteredVehicles = this.vehiclesDataSource.data.filter(c => c.id !== vehicle.id);
              this.vehiclesDataSource.data = filteredVehicles;
            },
            error: (err) => {
              this.snackBar.open('Erro ao deletar cliente: ' + err.message, 'Fechar', { duration: 5000 });
            }
          });
    }
  }

}



