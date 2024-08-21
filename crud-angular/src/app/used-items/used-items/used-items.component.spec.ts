import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsedItemsComponent } from './used-items.component';

describe('UsedItemsComponent', () => {
  let component: UsedItemsComponent;
  let fixture: ComponentFixture<UsedItemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsedItemsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(UsedItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
