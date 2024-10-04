import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardActions, MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';


import { CustomerService } from '../../services/customer.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-customer-form',
  standalone: true,
  imports: [MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatCardModule,
    MatToolbarModule,
    MatCardActions,
    MatButtonModule,
    RouterLink
  ],
  templateUrl: './customer-form.component.html',
  styleUrl: './customer-form.component.scss'
})

export class CustomerFormComponent implements OnInit {

  form: FormGroup;

  constructor(private formBuilder: FormBuilder,
    private service: CustomerService,
    private snackBar: MatSnackBar,
    private location: Location,
    private router: Router,
    private route: ActivatedRoute) {
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
    if (this.form.valid) {
      this.service
        .saveCustomer(this.form.value)
        .subscribe({next: () => {
          this.router.navigate(['/customer'],
          {queryParams: { message: 'Cliente salvo com sucesso!' }});
        },
        error: (err) => {
          this.snackBar.open('Erro ao salvar cliente: ' + err.message, '', {duration:3000});
        }
      });
    }
  }

}
