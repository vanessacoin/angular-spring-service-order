import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Vehicle } from './../model/vehicle';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  private readonly API = '/assets/vehicles.json';

  constructor(private httpClient: HttpClient) { }

  list() {
    return this.httpClient.get<Vehicle[]>(this.API);
  }
}
