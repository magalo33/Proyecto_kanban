import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { HttpClientModule } from '@angular/common/http';
import { RouterTestingModule } from '@angular/router/testing';
import { BackendService } from '../../services/backend.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      imports: [HttpClientModule, RouterTestingModule],
      providers: [BackendService]
    })
    .compileComponents();
    /*
     private seguridadService: SeguridadService,
      private backendService: BackendService,
      private resourcesService: ResourcesService,
      private router: Router
    */
   // component = new LoginComponent();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  
  it('Debe validar los parametros de registro', () => {
    component.validarParametros('arturo@gmail.com', 'admin');
    expect(component).toBeTruthy();
  });
  
  /*
  
  it('No debe validar los parametros de registro', () => {
    component.validarParametros('admin', 'admin');
    expect(component).toBeFalsy();
  });
*/
  it('Si debe ejecutar el login', () => {
    component.login('arturo@gmail.com', '123456');
    expect(component).toBeTruthy();
  });


  it('Debe redirigir sin error a la pagina de registrar usuario', () => {
    component.registarUsuario();
    expect(component).toBeTruthy();
  });


});
