import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistrarComponent } from './registrar.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { BackendService } from '../../services/backend.service';

describe('RegistrarComponent', () => {
  let component: RegistrarComponent;
  let fixture: ComponentFixture<RegistrarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegistrarComponent ],
      imports: [HttpClientModule, RouterTestingModule],
      providers: [BackendService]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Si debe validar los parametros', () => {
    component.validar("Arturo Calle","123456","123456","arturocallegmail.com");
    expect(component).toBeTruthy();
  });

});
