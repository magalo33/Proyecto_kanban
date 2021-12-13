import { HttpClientModule } from '@angular/common/http';
import { HttpTestingController } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { BackendService } from 'src/app/services/backend.service';

import { VerKanbanComponent } from './ver-kanban.component';

describe('VerKanbanComponent', () => {
  let httpTestingController: HttpTestingController;
  let component: VerKanbanComponent;
  let fixture: ComponentFixture<VerKanbanComponent>;

beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VerKanbanComponent ],
      imports: [HttpClientModule, RouterTestingModule],
      providers: [BackendService]
    })
    .compileComponents();
  });

  
  /*beforeEach(() => {
    fixture = TestBed.createComponent(VerKanbanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
*/




});
