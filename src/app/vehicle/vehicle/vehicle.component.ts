import { Component } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTabsModule } from '@angular/material/tabs';

@Component({
  selector: 'app-vehicle',
  standalone: true,
  imports: [MatTabsModule, MatInputModule, MatFormFieldModule, FormsModule, MatSelectModule, ReactiveFormsModule, MatButtonModule],
  templateUrl: './vehicle.component.html',
  styleUrl: './vehicle.component.scss'
})
export class VehicleComponent {
  selectCustomerFormControl = new FormControl('', Validators.required);
}

