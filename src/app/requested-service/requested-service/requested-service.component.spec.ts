import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestedServiceComponent } from './requested-service.component';

describe('RequestedServiceComponent', () => {
  let component: RequestedServiceComponent;
  let fixture: ComponentFixture<RequestedServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RequestedServiceComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RequestedServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
