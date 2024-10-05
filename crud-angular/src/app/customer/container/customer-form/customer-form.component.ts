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
  isEditing: boolean = false;

  constructor(private formBuilder: FormBuilder,
    private service: CustomerService,
    private snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute) {
    this.form = this.formBuilder.group({
      id: [null],
      name: [null],
      cpf: [null],
      phone: [null],
      email: [null]
    });
  }

  ngOnInit(): void {

    this.route.queryParams.subscribe(params => {
      if (params['id']) {
        this.isEditing = true;
        this.form.patchValue({
          id: params['id'],
          name: params['name'],
          cpf: params['cpf'],
          phone: params['phone'],
          email: params['email']
        });
      }
    });

  }

  onSave() {
    if (this.form.valid) {
      if (this.isEditing) {
        this.service.updateCustomer(this.form.value.id, this.form.value)
          .subscribe({
            next: () => {
              this.router.navigate(['/customer'],
              { queryParams: { message: 'Cliente atualizado com sucesso!' } });
            },
            error: (err) => {
              this.snackBar.open('Erro ao atualizar cliente: ' + err.message, 'Fechar', { duration: 5000 });
            }
          });
      } else {
        this.service.saveCustomer(this.form.value)
          .subscribe({
            next: () => {
              this.router.navigate(['/customer'],
              { queryParams: { message: 'Cliente salvo com sucesso!' } });
            },
            error: (err) => {
              this.snackBar.open('Erro ao salvar cliente: ' + err.message, '', { duration: 3000 });
            }
          });
      }
    }
  }

}
