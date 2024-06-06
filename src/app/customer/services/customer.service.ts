import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Customer } from './../model/customer';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private readonly API = '/assets/customers.json';

  constructor(private httpClient: HttpClient) { }

  list() {
    return this.httpClient.get<Customer[]>(this.API);
  }
}
