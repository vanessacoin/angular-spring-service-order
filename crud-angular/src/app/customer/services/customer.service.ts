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
    // Aqui você pode tratar o erro como desejar
    console.error('An error occurred:', error);
    return throwError(() => new Error('Erro na requisição. Por favor, tente novamente mais tarde.'));
  }

  save(record: Customer) {
    this.httpClient.post<Customer>(this.API, record);
  }

}
