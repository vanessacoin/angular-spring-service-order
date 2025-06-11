import { Customer } from "../../customer/model/customer";

export interface Vehicle {
  id: number;
  brand: string;
  model: string;
  plate: string;
  year: number;
  color: string;
  customer: Customer;
  km?: number;
}
