import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardActions, MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { NgxMaskDirective } from 'ngx-mask';

import { CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-customer-form',
  standalone: true,
  imports: [
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatCardModule,
    MatToolbarModule,
    MatCardActions,
    MatButtonModule,
    MatCheckboxModule,
    RouterLink,
    NgxMaskDirective,
  ],
  templateUrl: './customer-form.component.html',
  styleUrl: './customer-form.component.scss'
})
export class CustomerFormComponent implements OnInit {
  form: FormGroup;
  isEditing = false;

  constructor(
    private formBuilder: FormBuilder,
    private service: CustomerService,
    private snackBar: MatSnackBar,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.form = this.formBuilder.group({
      id: [null],

      name: ['', [Validators.required, Validators.minLength(2)]],

      hasCpf: [false],
      cpf: [{ value: '', disabled: true }],

      hasPhone: [false],
      phone: [{ value: '', disabled: true }],

      hasEmail: [false],
      email: [{ value: '', disabled: true }],
    });
  }

  ngOnInit(): void {
    this.bindOptionalField('hasCpf', 'cpf', [
      Validators.required,
      Validators.pattern(/^\d{11}$/),
    ]);

    this.bindOptionalField('hasPhone', 'phone', [
      Validators.required,
      Validators.pattern(/^\d{11}$/),
    ]);

    this.bindOptionalField('hasEmail', 'email', [
      Validators.required,
      Validators.email,
    ]);

    this.route.queryParams.subscribe(params => {
      if (params['id']) {
        this.isEditing = true;

        this.form.patchValue({
          id: params['id'],
          name: params['name'],

          hasCpf: !!params['cpf'],
          cpf: params['cpf'] ?? '',

          hasPhone: !!params['phone'],
          phone: params['phone'] ?? '',

          hasEmail: !!params['email'],
          email: params['email'] ?? '',
        });
      }
    });
  }

  private bindOptionalField(checkControl: string, fieldControl: string, validators: any[]) {
    const check = this.form.get(checkControl)!;
    const field = this.form.get(fieldControl)!;

    const apply = (enabled: boolean) => {
      if (enabled) {
        field.enable({ emitEvent: false });
        field.setValidators(validators);
      } else {
        field.reset('', { emitEvent: false });
        field.clearValidators();
        field.disable({ emitEvent: false });
      }
      field.updateValueAndValidity({ emitEvent: false });
    };

    apply(!!check.value);

    check.valueChanges.subscribe((enabled: boolean) => {
      apply(!!enabled);
    });
  }

  onSave() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const raw = this.form.getRawValue();

    const payload: any = {
      id: raw.id,
      name: raw.name,
      cpf: raw.hasCpf ? raw.cpf : null,
      phone: raw.hasPhone ? raw.phone : null,
      email: raw.hasEmail ? raw.email : null,
    };

    if (this.isEditing) {
      this.service.updateCustomer(payload.id, payload).subscribe({
        next: () => this.router.navigate(['/customer'], { queryParams: { message: 'Cliente atualizado com sucesso!' } }),
        error: (err) => this.snackBar.open('Erro ao atualizar cliente: ' + err.message, 'Fechar', { duration: 5000 }),
      });
    } else {
      this.service.saveCustomer(payload).subscribe({
        next: () => this.router.navigate(['/customer'], { queryParams: { message: 'Cliente salvo com sucesso!' } }),
        error: (err) => this.snackBar.open('Erro ao salvar cliente: ' + err.message, 'Fechar', { duration: 5000 }),
      });
    }
  }

  onCancel() {
    this.router.navigate(['/customer']);
  }
}
