import { Injectable } from '@angular/core';
import { UsuarioResponseInterface } from 'src/interfaces/usuarioresponse.interface';
import { BackendService } from './backend.service';
import { Router } from '@angular/router';
import Swal from'sweetalert2';
import { EstadoInterface } from 'src/interfaces/estado.interface';

@Injectable({
  providedIn: 'root'
})
export class ResourcesService {

  loggedUser:UsuarioResponseInterface;
  listaEstados:EstadoInterface[];
  error=0;

  constructor(
    private router: Router
    ) {
     this.listaEstados = [];
     this.loggedUser = this.iniciar();
   }


   public getLoggedUser():UsuarioResponseInterface{
    return this.loggedUser;
  }

  public setLoggedUser(loggedUser: UsuarioResponseInterface):void{
    this.loggedUser = loggedUser;
  }



  public iniciar():UsuarioResponseInterface{
    const usuarioResponse={
      error: 0,
      descripcion: '',
      usuario:{
        idusuario: 0,
        usuario: '',
        password: '',
        nombre: '',
        usuarioxrolesList:[
          {
            idusuarioxrol: 0,
            rol:{
              idrol: 0,
              descripcion: ''
            }
          }
        ],
        tareasList:[
          {
            idtarea: 0,
            descripcion:'',
            comentariosportareasList: [
              {
                idcomentariosportarea:0,
                comentario:'',
                idtarea:0
              }
            ],
            estado:{
              idestado: 0,
              descripcion: ''
            },
            idusuario:0
          }
        ]
      },
    };
    this.loggedUser=usuarioResponse;
    return usuarioResponse;
  }


  
    /*Muestra mensaje de error en un modal*/
    mostrarMensajeError(mensaje:string){
      Swal.fire({
        position:'center',
        icon: 'error',
        title: mensaje,
        showConfirmButton: true
      });    
    }
  
    /*Muestra mensaje de exito en un modal*/
    mostrarMensajeExito(mensaje:string){
      Swal.fire({
        position:'center',
        icon: 'success',
        title: mensaje,
        showConfirmButton: true
      });
    }

    /*Metodo que redirige la navegación*/
    navegar(path:string):boolean{    
      try{
        this.router.navigate([path]);
        return true;
      }catch(error){
        return false;
      }    
    }

}