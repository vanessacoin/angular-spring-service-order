import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Vehicle } from './../model/vehicle';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  private readonly API = 'api/vehicles';

  constructor(private readonly httpClient: HttpClient) { }

  list(): Observable<Vehicle[]>{
    return this.httpClient.get<Vehicle[]>(this.API);
  }

  getVehicleById(id: number): Observable<Vehicle> {
    return this.httpClient.get<Vehicle>(`${this.API}/${id}`);
  }

  saveVehicle(vehicle: Vehicle): Observable<Vehicle> {
    return this.httpClient.post<Vehicle>(`${this.API}`, vehicle);
  }

  updateVehicle(id: number, vehicle: Vehicle): Observable<Vehicle> {
    return this.httpClient.put<Vehicle>(`${this.API}/${id}`, vehicle);
  }

  deleteVehicle(id: number): Observable<Vehicle> {
    return this.httpClient.delete<Vehicle>(`${this.API}/${id}`);
  }
}

