import { Component, OnInit } from '@angular/core';
import { SequentialService } from '../sequential/sequential.service';

@Component({
  selector: 'app-sequential',
  standalone: true,
  imports: [],
  templateUrl: './sequential.component.html',
  styleUrl: './sequential.component.scss'
})

export class SequentialComponent implements OnInit{
  sequentialValue: number = 0;

  constructor(private sequentialService: SequentialService) { }

  ngOnInit(): void {
    this.sequentialValue = this.sequentialService.getSequentialValue();
  }
}

