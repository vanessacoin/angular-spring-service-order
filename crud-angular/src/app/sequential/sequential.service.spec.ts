import { TestBed } from '@angular/core/testing';

import { SequentialService } from './sequential.service';

describe('SequentialService', () => {
  let service: SequentialService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SequentialService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
