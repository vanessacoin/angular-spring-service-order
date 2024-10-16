import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardActions, MatCardModule } from '@angular/material/card';
import { MatOptionModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { CustomerService } from '../../../customer/services/customer.service';
import { VehicleService } from '../../services/vehicle.service';
import { Customer } from './../../../customer/model/customer';

@Component({
  selector: 'app-vehicle-form',
  standalone: true,
  imports: [MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatCardModule,
    MatToolbarModule,
    MatCardActions,
    MatButtonModule,
    RouterLink,
    MatSelectModule,
    MatOptionModule
  ],
  templateUrl: './vehicle-form.component.html',
  styleUrl: './vehicle-form.component.scss'
})

export class VehicleFormComponent implements OnInit{

  customers: Customer[] = [];

  form: FormGroup;
  isEditing: boolean = false;

  constructor(
    private readonly service: VehicleService,
    private readonly customerService: CustomerService,
    private readonly formBuilder: FormBuilder,
    private readonly snackBar: MatSnackBar,
    private readonly router: Router,
    private readonly route: ActivatedRoute
  ) {
      this.form = this.formBuilder.group({
        id: [null],
        brand: [null],
        model: [null],
        plate: [null],
        year:[null],
        color: [null],
        customer: [null],
      })
  }

  ngOnInit(): void {

    this.customerService.list().subscribe((data: Customer[]) => {
      this.customers = data;
    });

    this.route.queryParams.subscribe(params => {
      if (params['id']) {
        this.isEditing = true;
        this.form.patchValue({
          id: params['id'],
          brand: params['brand'],
          model: params['model'],
          plate: params['plate'],
          year: params['year'],
          color: params['color'],
          customer: this.customers.find(cust => cust.id === params['customer'])
        });
      }
    });

  }

  onSave() {
    if (this.form.valid) {

      const vehicleData = {
        ...this.form.value,
        customer: { id: this.form.value.customer }
      };

      if (this.isEditing) {
        this.service.updateVehicle(this.form.value.id, vehicleData)
          .subscribe({
            next: () => {
              this.router.navigate(['/vehicle'], { queryParams: { message: 'Veículo atualizado com sucesso!' } });
            },
            error: (err) => {
              this.snackBar.open('Erro ao atualizar veículo: ' + err.message, 'Fechar', { duration: 5000 });
            }
          });
      } else {
        this.service.saveVehicle(vehicleData)
          .subscribe({
            next: () => {
              this.router.navigate(['/vehicle'], { queryParams: { message: 'Veículo salvo com sucesso!' } });
            },
            error: (err) => {
              this.snackBar.open('Erro ao salvar veículo: ' + err.message, '', { duration: 5000 });
            }
          });
      }
    }
  }

  onCancel() {
    this.router.navigate(['/vehicle']);
  }

}
