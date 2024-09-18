import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardActions, MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';

@Component({
  selector: 'app-customer-form',
  standalone: true,
  imports: [MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatCardModule,
    MatToolbarModule,
    MatCardActions,
    MatButtonModule],
  templateUrl: './customer-form.component.html',
  styleUrl: './customer-form.component.scss'
})

export class CustomerFormComponent implements OnInit {

  form: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.form = this.formBuilder.group({
      name: [null],
      cpf: [null],
      phone: [null],
      email: [null]
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {

  }

  onCancel() {

  }

}
