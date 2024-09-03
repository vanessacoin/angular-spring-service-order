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
  vehicleKm: string;
  vehicleYear: string;
  vehicleColor: string;
  vehicleCustomerId: string;
  requestedServices: RequestedService[];
  usedItems: UsedItem[];
}

// adicionar as interfaces para RequestedService e UsedItem, se necessário
export interface RequestedService {
  // Definição dos campos do serviço solicitado
}

export interface UsedItem {
  // Definição dos campos dos itens usados
}
