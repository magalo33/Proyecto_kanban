import { Component, OnInit } from '@angular/core';
import { BackendService } from 'src/app/services/backend.service';
import { ResourcesService } from 'src/app/services/resources.service';
import { SeguridadService } from 'src/app/services/seguridad.service';
import { UsuarioResponseInterface } from 'src/interfaces/usuarioresponse.interface';


@Component({
  selector: 'app-registrar',
  templateUrl: './registrar.component.html',
  styleUrls: ['./registrar.component.css']
})

export class RegistrarComponent implements OnInit {
  usuario:string;
  nombre:string;
  password:string;
  confirmacionPassword:string;
  constructor(
    private seguridadService: SeguridadService,
    private resourcesService: ResourcesService,
    private backendService: BackendService
  ) {
      this.usuario = "";
      this.nombre = "";
      this.password = "";
      this.confirmacionPassword = "";
  }

  ngOnInit(): void {
  }

  /*Metodo para registro de información*/
  registrar(usuario:string,nombre:string,password:string,confirmacionPassword:string){
    if(this.validar(nombre,password,confirmacionPassword,usuario)){
      const request = {
        usuario: usuario.trim(),
        nombre: nombre.trim(),
        password: password.trim()
      };
      let headerRequest= {
        usuarioRequest: {
            usuario: request
        }
      }; 
      headerRequest =  this.seguridadService.agregarFecha2(headerRequest);
      const encriptado = this.seguridadService.getEncrypt2(headerRequest.toString());
      const encriptedPassword = this.seguridadService.encriptar(password.trim());
      request.password = encriptedPassword;
      let loginObject:any = {
        usuario:request
      };
      this.registrarUsuario(loginObject,encriptado);
    }
  }

     /*Metodo de registro de usuario*/
     registrarUsuario(loginObject:any,encriptado:string){
      this.backendService.registrarUsuario(loginObject,encriptado)
      .subscribe((response: UsuarioResponseInterface) => {  
        if(response.error == 0){
          this.resourcesService.mostrarMensajeExito("Registro de usuario ok");
          this.resourcesService.loggedUser = response; 
          this.resourcesService.navegar(`../login`);  
        }else{
          this.resourcesService.mostrarMensajeError(response.descripcion);            
        }
      }, (error) => {
        console.log(error);
      });
    }



  /*Valida los campos del formulario*/
  validar(nombre:string,password:string,confirmacionPassword:string,usuario:string):boolean{
    let validado = true;
    if(nombre.trim() == ''){
      this.resourcesService.mostrarMensajeError('El nombre es obligatorio');
      validado = false;
    }else{
      if(password != confirmacionPassword){
        this.resourcesService.mostrarMensajeError('Las contraseñas no coinciden');
        validado = false;
      }else{
        if(password.trim() == ''){
          this.resourcesService.mostrarMensajeError('La contraseña es obligatoria');
          validado = false;
        }else{
          if(usuario.trim() == ''){
            this.resourcesService.mostrarMensajeError('El email es obligatorio');
            validado = false;
          }else{
            if(usuario.indexOf('@') < 0){
              this.resourcesService.mostrarMensajeError('El email no es valido');
              validado = false;
            }else{
              if(usuario.indexOf('.') < 0){
                this.resourcesService.mostrarMensajeError('El email no es valido');
                validado = false;
              }else{
                if(nombre.length > 100){
                  this.resourcesService.mostrarMensajeError('El nombre no puede contener mas de 100 caracteres');
                  validado = false;
                }else{
                  if(usuario.length > 100){
                    this.resourcesService.mostrarMensajeError('El email no puede contener mas de 100 caracteres');
                    validado = false;
                  }
                }
              }
            }
          }
        }
      }
    }    
    return validado;
  }



}
