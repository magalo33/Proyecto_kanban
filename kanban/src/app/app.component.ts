import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ResourcesService } from './services/resources.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'kanban';
  constructor(
    private router: Router,
    private resourcesService: ResourcesService) { 
  }

  inicio(){
    this.resourcesService.iniciar();
    this.router.navigate([`../login`]);
  }
}
