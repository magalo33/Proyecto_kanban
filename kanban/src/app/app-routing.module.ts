import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VerKanbanComponent } from './components/ver-kanban/ver-kanban.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrarComponent } from './components/registrar/registrar.component';

const routes: Routes = [
  {path:'registrar',component:RegistrarComponent},
  {path:'login',component:LoginComponent},
  {path:'verkanban',component:VerKanbanComponent},
  {path:'', component:LoginComponent},  
  {path:'**',component:LoginComponent},
  
  ]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
