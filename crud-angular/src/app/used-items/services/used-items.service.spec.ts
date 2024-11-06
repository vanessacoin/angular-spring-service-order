import { TestBed } from '@angular/core/testing';

import { UsedItemsService } from './used-items.service';

describe('UsedItemsService', () => {
  let service: UsedItemsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UsedItemsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
