import { MatSnackBar } from '@angular/material/snack-bar';
import { AsyncPipe, NgIf } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Observable } from 'rxjs';

import { Customer } from '../../model/customer';
import { CustomerService } from '../../services/customer.service';

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
    AsyncPipe,
    RouterLink],
  templateUrl: './customer.component.html',
  styleUrl: './customer.component.scss',
  providers: [CustomerService]
})

export class CustomerComponent implements OnInit {
  @Input() customer_list: Customer[] = [];
  @Output() add = new EventEmitter(false);
  @Output() edit = new EventEmitter(false);
  @Output() remove = new EventEmitter(false);

  //customersDataSource: MatTableDataSource<Customer> = new MatTableDataSource();
  customersDataSource: MatTableDataSource<Customer> = new MatTableDataSource<Customer>([]);

  customers$: Observable<Customer[]>;
  readonly displayedColumns = ['name', 'cpf', 'phone', 'email', 'actions'];

  constructor(
    private service: CustomerService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {
    this.customers$ = this.service.list();
  }

  ngOnInit(): void {
    this.customers$.subscribe(customers => {
      this.customersDataSource.data = customers;
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

  onUpdate(customer: Customer) {
    this.router.navigate(['new'], { relativeTo: this.route,
      queryParams: {
        id: customer._id,
        name: customer.name,
        cpf: customer.cpf,
        phone: customer.phone,
        email: customer.email
      }
     });
  }

  onDelete(customer: Customer) {
    if(confirm('Deletar cliente ' + customer.name + '?')){
      this.service.deleteCustomer(customer._id)
          .subscribe({
            next: () => {
              this.snackBar.open('Cliente deletado com sucesso!', '', { duration: 3000 });
              const filteredCustomers = this.customersDataSource.data.filter(c => c._id !== customer._id);
              this.customersDataSource.data = filteredCustomers;
            },
            error: (err) => {
              this.snackBar.open('Erro ao deletar cliente: ' + err.message, 'Fechar', { duration: 5000 });
            }
          });
    }
  }

}
