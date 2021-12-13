import {  ComponentFixture, TestBed } from '@angular/core/testing';
import { ResourcesService } from './resources.service';
import { RouterTestingModule } from '@angular/router/testing';
import { BackendService } from './backend.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('ResourcesService', () => {
  let service: ResourcesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule,RouterTestingModule],
      providers: [BackendService]
    });    
  });




  //let fixture: ComponentFixture<ResourcesService>;
//
/*
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResourcesService ],
      imports: [HttpClientModule, RouterTestingModule],
      providers: [BackendService]
    })
    .compileComponents();
    
     private seguridadService: SeguridadService,
      private backendService: BackendService,
      private resourcesService: ResourcesService,
      private router: Router
    
   // component = new LoginComponent();
  });*/



  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResourcesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
