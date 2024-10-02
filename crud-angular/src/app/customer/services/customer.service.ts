import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';

import { catchError } from 'rxjs/operators';

import { Customer } from './../model/customer';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private readonly API = '/api/customers';

  constructor(private httpClient: HttpClient) { }

  list(): Observable<Customer[]> {
    return this.httpClient.get<Customer[]>(this.API).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    console.error('An error occurred:', error);
    return throwError(() => new Error('Erro na requisição.'));
  }

  saveCustomer(customer: Customer): Observable<Customer> {
    return this.httpClient.post<Customer>(`${this.API}`, customer);
  }

}
