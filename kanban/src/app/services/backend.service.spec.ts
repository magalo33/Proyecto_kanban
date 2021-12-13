import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { BackendService } from './backend.service';

describe('BackendService', () => {
  let service: BackendService;

  beforeEach(() => {
    TestBed.configureTestingModule({imports: [HttpClientModule],
      providers: [BackendService]});
    service = TestBed.inject(BackendService);
  });

  it('should be created', () => {
    const service: BackendService = TestBed.get(BackendService);
    expect(service).toBeTruthy();
  });
});
