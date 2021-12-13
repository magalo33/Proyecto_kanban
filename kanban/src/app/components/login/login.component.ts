import { Component, OnInit } from '@angular/core';
import { SeguridadService } from 'src/app/services/seguridad.service';
import { ResourcesService } from 'src/app/services/resources.service';
import { BackendService } from 'src/app/services/backend.service';
import { UsuarioResponseInterface } from 'src/interfaces/usuarioresponse.interface';
//http://190.27.52.244:8181/html

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit { 
  usuarioLogin:string;
  contrasenia:string;
  constructor(
    private seguridadService: SeguridadService,    
    private resourcesService: ResourcesService,
    private backendService: BackendService,
    ) { 
      this.usuarioLogin = "";
      this.contrasenia = "";    
  }

  ngOnInit(): void {
  }

  /*Metodo de login*/
  login(usuario:string,password:string):boolean{    
    let ejecutarLogin = false;
    if(this.validarParametros(usuario,password)){
      let headerRequest= {
        usuarioRequest: {
            usuario: {
                usuario: usuario.trim(),
                password: password.trim()
            }
        }
      };      
      headerRequest =  this.seguridadService.agregarFecha2(headerRequest);
      const encriptado = this.seguridadService.getEncrypt2(headerRequest.toString());
      const encriptedPassword = this.seguridadService.encriptar(password.trim());
        let loginObject:any = {
          usuario:{
            usuario: usuario.trim(),
            password:encriptedPassword
          }      
        };
        ejecutarLogin = true;
        this.loguearUsuario(loginObject,encriptado);
    }
    return ejecutarLogin;
  }


  /*Validas los parametros de registro*/
  validarParametros(usuario:string,password:string):boolean{
    let parametrosValidos = true;
    if(usuario.trim().length == 0){
      parametrosValidos = false;
      this.resourcesService.mostrarMensajeError('Debe ingresar el usuario');       
    }else{
      if(password.trim().length == 0){
        parametrosValidos = false;
        this.resourcesService.mostrarMensajeError('Debe ingresar el password');      
      }else{
        parametrosValidos = this.validarMail(usuario);
      }
    }
    return parametrosValidos;
  }
  
  /*Se conecta al servicio para loguear el usuario*/
  loguearUsuario(loginObject:any,encriptado:string):boolean{
    let ejecutarLogin = true;
    try{
      this.backendService.postLogin(loginObject,encriptado)
      .subscribe((response: UsuarioResponseInterface) => {
      if(response.error == 0){
        this.resourcesService.mostrarMensajeExito("Login ok");
        this.resourcesService.setLoggedUser(response);
        this.resourcesService.navegar(`../verkanban`);
      }else{
        this.resourcesService.mostrarMensajeError(response.descripcion); 
      }      
    }, (error) => {
      this.resourcesService.mostrarMensajeError("Se presento un error de conexión al tratar de loguearse"); 
      console.log(error);
    });
    return true;
    }catch(error){
      ejecutarLogin = false;
    }   
    return ejecutarLogin;
  }


  



  /*Valida emailo*/
  validarMail(email:string):boolean{
    let validado = true;
    if(email.indexOf('@') < 0){
      this.resourcesService.mostrarMensajeError('El email no es valido');
      validado = false;
    }else{
      if(email.indexOf('.') < 0){
        this.resourcesService.mostrarMensajeError('El email no es valido');
        validado = false;
      }
    }
    return validado;
  }



  /*Metodo para llamado del formulario de registro de usuario*/
  registarUsuario():boolean{    
    try{
     this.resourcesService.navegar(`../registrar`);
    return true;
    }catch(error){
      return false;
    }    
  }

}
