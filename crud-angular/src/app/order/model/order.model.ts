import { RequestedService } from "../../requested-service/requested-service/requested-service.component";
import { UsedItem } from "../../used-items/used-items/used-items.component";

export interface Order {
  id: number;
  orderDate: Date;
  customerId: string;
  customerName: string;
  customerCpf: string;
  customerPhone: string;
  customerEmail: string;
  vehicleId: string;
  vehicleBrand: string;
  vehicleModel: string;
  vehiclePlate: string;
  vehicleKm: number;
  vehicleYear: number;
  vehicleColor: string;
  vehicleCustomerId: string;
  requestedServices: RequestedService[];
  usedItems: UsedItem[];
  laborCost: number;
  totalOrder: number;
}
