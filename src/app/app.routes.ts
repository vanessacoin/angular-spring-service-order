import { Routes } from '@angular/router';

export const routes: Routes = [
  {path: 'customer', loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule)},
  {path: 'vehicle', loadChildren: () => import('./vehicle/vehicle.module').then(m => m.VehicleModule)},
  {path: 'order', loadChildren: () => import('./order/order.module').then(m => m.OrderModule)},
  {path: 'sequential', loadChildren: () => import('./sequential/sequential.module').then(m => m.SequentialModule)}
];
