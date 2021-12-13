import { Component, OnInit } from '@angular/core';
import { UsuarioResponseInterface } from 'src/interfaces/usuarioresponse.interface';

import { ComentariosportareaInterface } from 'src/interfaces/comentariosportarea.interface';
import { BackendService } from   'src/app/services/backend.service';
import { SeguridadService } from 'src/app/services/seguridad.service';
import { ResourcesService } from 'src/app/services/resources.service';
import * as jQuery from 'jquery';
import { Router } from '@angular/router';
import { TareaInterface } from 'src/interfaces/tarea.interface';
import { EstadoInterface } from 'src/interfaces/estado.interface';

@Component({
  selector: 'app-ver-kanban',
  templateUrl: './ver-kanban.component.html',
  styleUrls: ['./ver-kanban.component.css']
})

export class VerKanbanComponent implements OnInit {
  
  agregarTareaVisible=false;
  tarea:TareaInterface;
  tareaPorEditar:TareaInterface;
  comentarioRequest:any;
  listaEstados:EstadoInterface[];
  error = 0;

  constructor(
    public seguridadService: SeguridadService,
    public resourcesService: ResourcesService,
    public backendService: BackendService,
    private router: Router
    ) { 
      this.listaEstados = [];
      this.comentarioRequest = {
        comentario:"",
        idtarea:0
      }
      this.tarea = {
        idtarea:0,
        descripcion:"",
        comentariosportareasList:
        [
          this.comentarioRequest
        ],
        estado:
        {
          idestado:1,
          descripcion:"por_hacer"
        },
        idusuario:this.resourcesService.loggedUser.usuario.idusuario
      };
      this.tareaPorEditar = this.tarea;
      this.ordenarListaTareas();
      this.cargarEstados();
  }

  cargarEstados(){
    this.backendService.getEstado()
    .subscribe((listaEestado:EstadoInterface[]) => {
     this.listaEstados = listaEestado;
    }, (error) => {
      this.resourcesService.mostrarMensajeError("Se presento un error al cargar los estados");
    });
  }
  
  
  /*Carga inicial de variables*/
  iniciarRequest():boolean{
    let iniciarrequest = true;
    try{
      this.comentarioRequest = {
        comentario:"",
        idtarea:0
      }
      this.tarea = {
        idtarea:0,
        descripcion:"",
        comentariosportareasList:
        [
          this.comentarioRequest
        ],
        estado:
        {
          idestado:1,
          descripcion:"por_hacer"
        },
        idusuario:this.resourcesService.loggedUser.usuario.idusuario
      };
    }catch(error){
      iniciarrequest = false;
    }
    return iniciarrequest;
  }

  ngOnInit(): void {
  }

  /*Carga la descripcion del la tarea seleccionada para mostrarlo en un modal*/
  verComentarios(idtarea:number):boolean{
    let vercomentarios = true;
    try{
      const tareasList = this.resourcesService.loggedUser.usuario.tareasList;
      let txt = "<table class='table table-striped'>";
      tareasList.forEach((tarea:TareaInterface)=>{
          if(tarea.idtarea == idtarea){
            const comentariosportareasList = tarea.comentariosportareasList;
            comentariosportareasList.forEach((comentario:ComentariosportareaInterface)=>{
              txt = txt + "<tr><td align='justify'>"+comentario.comentario+"</td></tr>";
            }
          );
          }
        }
      );
      txt = txt + "</table>";
      this.resourcesService.mostrarMensajeExito(txt);
    }catch(error){
      vercomentarios = false;
    }
    return vercomentarios;
  }

  /*Valida la posibilidad de registro de tarea, muestra el formulario que permite esta accion*/
  registrarTarea():boolean{
    let registrar = true;
    try{
      if(this.resourcesService.loggedUser.usuario.nombre.trim() == ""){
        this.resourcesService.mostrarMensajeError("Debe estar logueado para registrar una tarea");
        this.router.navigate([`../login`]);
      }else{
        if(!this.agregarTareaVisible){
          this.agregarTareaVisible = true;
          let divAgregarTarea = document.getElementById('div-agregar-tarea') as HTMLInputElement;
          let botonAgregarTarea = document.getElementById('boton-agregar-tarea') as HTMLInputElement;
          divAgregarTarea?.classList.remove('ocultar');
          botonAgregarTarea?.classList.remove('mostrar');
          botonAgregarTarea?.classList.add('ocultar');
        }
      }     
    }catch(error){
      registrar = false;
    }
    return registrar;       
  }

  /*Desata el evento de cancelación de registro de tarea*/
  cancelarRegistroTarea(){
    let cancelar = true;
    try{
      if(this.agregarTareaVisible){
        this.cancelar();
       }
    }catch(error){
      cancelar = false;
    }
    return cancelar;
  }

  /*Cierra el formulario de registro de tarea*/
  cancelar():boolean{
    let cancelar = true;
    try{
      this.agregarTareaVisible = false;
      let divAgregarTarea = document.getElementById('div-agregar-tarea') as HTMLInputElement;
      let botonAgregarTarea = document.getElementById('boton-agregar-tarea') as HTMLInputElement;
      botonAgregarTarea?.classList.remove('ocultar');
      botonAgregarTarea?.classList.add('mostrar');
      divAgregarTarea?.classList.remove('mostrar');
      divAgregarTarea?.classList.add('ocultar');
      this.iniciarRequest();
    }catch(error){
      cancelar = false;
    }
    return cancelar;
  }

  /*Desata el evento de registro de tarea*/
  aceptarRegistroTarea(){
    let comentario = "(Creado el ".concat(this.seguridadService.getFecha()).concat(") ").concat(this.comentarioRequest.comentario);
    this.comentarioRequest.comentario = comentario;
    if(this.tarea.descripcion.trim().length == 0){
      this.resourcesService.mostrarMensajeError('Debe ingresar la descripción de la tarea');
    }else{
      if(this.comentarioRequest.comentario.trim().length == 0){
        this.resourcesService.mostrarMensajeError('Debe ingresar un comentario');
      }else{
        let tareaRegistro ={
          tareaRequest:{
            tarea:this.tarea
          }          
        };
        tareaRegistro = this.seguridadService.agregarFecha2(tareaRegistro);
        const encriptado = this.seguridadService.getEncrypt2(tareaRegistro.toString());    
        let bodyRegistrarTarea ={
          tarea:this.tarea
        };       
       this.backendService.postTarea(bodyRegistrarTarea,encriptado)
       .subscribe((response: UsuarioResponseInterface) => {  
         if(response.error == 0){
          this.resourcesService.mostrarMensajeExito("Login ok");
           this.resourcesService.loggedUser = response;
           this.ordenarListaTareas();
           this.cancelar();
         }else{
          this.resourcesService.mostrarMensajeExito("Registro ok");            
         }
       }, (error) => {
         console.log(error);
       });

      }
    }
    }

    /*Desata el evento de avance de tarea*/
    avanzarTarea(tarea:TareaInterface){
      tarea.estado.idestado = (tarea.estado.idestado+1);
      tarea.comentariosportareasList[0].comentario = 
      tarea.comentariosportareasList[0].comentario
      .concat("\n(Avanza al estatus ").concat((this.obtenerEstado(tarea.estado.idestado)).toString()).concat(" en ").concat( this.seguridadService.getFecha()).concat(")\n")
      this.moverTarea(tarea);
    }

    /*Desata el evento de etroceso de tarea*/
    retrocederTarea(tarea:TareaInterface){
      tarea.estado.idestado = (tarea.estado.idestado-1);
      tarea.comentariosportareasList[0].comentario = 
      tarea.comentariosportareasList[0].comentario
      .concat("\n(Retrocede al estatus ").concat((this.obtenerEstado(tarea.estado.idestado)).toString()).concat(" en ").concat( this.seguridadService.getFecha()).concat(")\n")
      this.moverTarea(tarea);
    }

    /*Mueve una tarea hacia adelante o hacia atras*/
    moverTarea(tarea:TareaInterface){
      let bodyRequest = this.getBodyRequest(tarea);
      let encriptado = this.getHeaderRequestEncriptado(bodyRequest);
      this.backendService.putTarea(bodyRequest,encriptado)
      .subscribe((response: UsuarioResponseInterface) => {  
        if(response.error == 0){
          this.resourcesService.mostrarMensajeExito("Edición ok");
          this.resourcesService.loggedUser = response;
          this.ordenarListaTareas();
          this.cancelar();
          this.tareaPorEditar = {
            idtarea:0,
            descripcion:"",
            comentariosportareasList:
            [
              this.comentarioRequest
            ],
            estado:
            {
              idestado:1,
              descripcion:"por_hacer"
            },
            idusuario:this.resourcesService.loggedUser.usuario.idusuario
          };
        }else{
          this.resourcesService.mostrarMensajeError(response.descripcion);        
        }
      }, (error) => {
        console.log(error);
      });
    }

    /*Desata el evento de edicion de comentario*/
    editarComentarioPorTarea(tarea:TareaInterface){
      let bodyRequest={
        usuario:{
          idusuario:this.resourcesService.loggedUser.usuario.idusuario
        },
        tarea:tarea
      }
      let headerRequest = {
        editarTareaRequestDto:bodyRequest
      };
      headerRequest =  this.seguridadService.agregarFecha2(headerRequest);
      const encriptado = this.seguridadService.getEncrypt2(headerRequest.toString());
      this.backendService.putComentarioportarea(bodyRequest,encriptado)
      .subscribe((response: UsuarioResponseInterface) => {  
        if(response.error == 0){
          this.resourcesService.mostrarMensajeExito("Edición ok");
          this.resourcesService.loggedUser = response;
          this.ordenarListaTareas();
          this.cancelar();
          this.closeModal();
          this.limpiarModales();
          this.tareaPorEditar = {
            idtarea:0,
            descripcion:"",
            comentariosportareasList:
            [
              this.comentarioRequest
            ],
            estado:
            {
              idestado:1,
              descripcion:"por_hacer"
            },
            idusuario:this.resourcesService.loggedUser.usuario.idusuario
          };
        }else{
          this.resourcesService.mostrarMensajeError("Se presentó un error al tratar de editar el comentario");       
        }
      }, (error) => {
        console.log(error);
      });
    }

    /*Desata el evento de eliminacion de tarea*/
    aceptarEliminarTarea(){   
      let aceptar = true;
      try{
        let idEliminarPasswordInput = document.getElementById('id-eliminar-password-Input') as HTMLInputElement;
      ($('#modaleliminartarea') as any).modal('hide');
      this.validarCredenciales(idEliminarPasswordInput.value,2,"");
      }catch(error){
        aceptar = false
      }      
      return aceptar;   
      
    }

    /*Abre modal de eliminacion de tarea*/
    abrirModalEliminarTarea(tarea:TareaInterface):boolean{
      let abrir = true;
      try{
        this.tareaPorEditar = tarea;
        jQuery.noConflict();
        ($('#modaleliminartarea') as any).modal('show');
      }catch(error){
        abrir = false
      }      
      return abrir;
    }

    /*Elimina la tarea seleccionada*/
    eliminarTarea(tarea:TareaInterface){  
    let bodyRequest={
        usuario:{
          idusuario:this.resourcesService.loggedUser.usuario.idusuario
        },
        tarea:tarea
      }
      let headerRequest = {
        editarTareaRequestDto:bodyRequest
      };
      headerRequest =  this.seguridadService.agregarFecha2(headerRequest);
      const encriptado = this.seguridadService.getEncrypt2(headerRequest.toString());
      this.backendService.deleteTarea(encriptado)
      .subscribe((response: UsuarioResponseInterface) => {  
        if(response.error == 0){
          this.resourcesService.mostrarMensajeExito("Proceso ok"); 
          this.resourcesService.loggedUser = response;
          this.ordenarListaTareas();
          this.cancelar();
          this.limpiarModales();
        }else{
          this.resourcesService.mostrarMensajeError(response.descripcion);             
        }
      }, (error) => {
        console.log(error);
      });
    }

    /*Captura la tarea por editar*/
    editarTarea(tareaParam:TareaInterface):boolean{
      let editar = true;
      try{
        this.tareaPorEditar = tareaParam;
        const tareasList = this.resourcesService.loggedUser.usuario.tareasList;
        let txt = "";
        tareasList.forEach((tarea:TareaInterface)=>{
            if(tarea.idtarea == tareaParam.idtarea){
              const comentariosportareasList = tarea.comentariosportareasList;
              comentariosportareasList.forEach((comentario:ComentariosportareaInterface)=>{
                txt = txt + comentario.comentario;
              }
            );
            }
          }
        );
        let idComentarioInput = document.getElementById('id-comentario-input') as HTMLInputElement;
        idComentarioInput.value = txt;
        jQuery.noConflict();
          ($('#modaleditartarea') as any).modal('show');
      }catch(error){editar = false}
      return editar;
    }

    /*Captura el password del usuario y el texto por editar de la tarea en curso*/
    aceptarEditarTarea(){
      let idNuevoComentarioInput = document.getElementById('id-nuevo-comentario-input') as HTMLInputElement;      
      let idPasswordComentario = document.getElementById('id-password-comentario') as HTMLInputElement;
      ($('#modaleditartarea') as any).modal('hide');      
      this.validarCredenciales(idPasswordComentario.value,1,idNuevoComentarioInput.value);
    }

    /*Limpia los campos de los modales*/
    limpiarModales():boolean{
      let limpiar = true;
      try{
        let idComentarioInput = document.getElementById('id-comentario-input') as HTMLInputElement;
        let idNuevoComentarioInput = document.getElementById('id-nuevo-comentario-input') as HTMLInputElement;      
        let idPasswordComentario = document.getElementById('id-password-comentario') as HTMLInputElement;
        let idEliminarPasswordInput = document.getElementById('id-eliminar-password-Input') as HTMLInputElement;
        idPasswordComentario.value = "";
        idEliminarPasswordInput.value = "";
        idNuevoComentarioInput.value = "";
        idComentarioInput.value = "";
      }catch(error){
        limpiar = false;
      }
      return limpiar;
    }

    /*cierra modal y reinicia variables*/
    closeModal(): boolean{
      let cerrar = true;
      try{
        this.limpiarModales();
      ($('#modaleditartarea') as any).modal('hide');
      ($('#modaleliminartarea') as any).modal('hide');      
      this.tareaPorEditar  = {
        idtarea:0,
        descripcion:"",
        comentariosportareasList:
        [
          this.comentarioRequest
        ],
        estado:
        {
          idestado:1,
          descripcion:"por_hacer"
        },
        idusuario:this.resourcesService.loggedUser.usuario.idusuario
      };      
      this.tareaPorEditar = {
        idtarea:0,
        descripcion:"",
        comentariosportareasList:
        [
          this.comentarioRequest
        ],
        estado:
        {
          idestado:1,
          descripcion:"por_hacer"
        },
        idusuario:this.resourcesService.loggedUser.usuario.idusuario
      };
      }catch(error){
        cerrar = false;
      }      
      return cerrar;
    }
    
    /*Ordena la lista de tareas*/
    ordenarListaTareas():boolean{
      let ordenar = true;
      try{
        this.resourcesService.loggedUser.usuario.tareasList.sort((a,b)=>{
          if(a.idtarea > b.idtarea){
            return 1;
          }else{
            return -1;
          }
        });
      }catch(error){
        ordenar = false;
      }      
      return ordenar;
    }

    /*Valida credenciales de usuario para editar y/o eliminar tarea*/
    validarCredenciales(password:string,opcionTarea:number,nuevoComentario:string){      
        this.backendService.postLogin(this.getBodyValidatedUser(password),this.getHeaderEncriptedValidatedUser(password))
          .subscribe((response: UsuarioResponseInterface) => {  
            if(response.error == 0){
              this.resourcesService.mostrarMensajeExito("Credenciales ok");
              if(opcionTarea == 1){
                this.tareaPorEditar.comentariosportareasList[0].comentario = this.tareaPorEditar.comentariosportareasList[0].comentario.concat("\n")
                .concat("(Agregado en ".concat( this.seguridadService.getFecha()).concat(") "))
                .concat(nuevoComentario);
                this.editarComentarioPorTarea(this.tareaPorEditar);
                this.limpiartareaParam();
              }else{
                this.eliminarTarea(this.tareaPorEditar);
              }    
            }else{
              this.resourcesService.mostrarMensajeError('No se pudieron validar las credenciales'); 
              this.limpiarModales();
              this.limpiartareaParam();
            }
          }, (error) => {
            console.log(error);
          }); 
    }

    /*Limpia el objeto tareaPorEditar*/
    limpiartareaParam():boolean{
      let limpiar = true;
      try{
        this.tareaPorEditar = {
          idtarea:0,
          descripcion:"",
          comentariosportareasList:
          [
            this.comentarioRequest
          ],
          estado:
          {
            idestado:1,
            descripcion:"por_hacer"
          },
          idusuario:this.resourcesService.loggedUser.usuario.idusuario
        };
      }catch(error){
        limpiar = false;
      }      
      return limpiar;
    }

    

    /*Obtiene el parametro body para la edicion de tarea*/
    getBodyRequest(tarea:TareaInterface):any{
      let bodyRequest={
        usuario:{
          idusuario:this.resourcesService.loggedUser.usuario.idusuario
        },
        tarea:tarea
      };
      return bodyRequest;
    }

    /*Obtiene el parametro header para la edición de tarea*/
    getHeaderRequestEncriptado(bodyRequest:any):string{
      let headerRequest = {
        editarTareaRequestDto:bodyRequest
      };
      headerRequest =  this.seguridadService.agregarFecha2(headerRequest);
      return this.seguridadService.getEncrypt2(headerRequest.toString());
    }

    /*Retorna el parametro header encriptado para validacion de credenciales*/
    getHeaderEncriptedValidatedUser(password:string):string{
      let headerRequest= {
        usuarioRequest: {
            usuario: {
                usuario: this.resourcesService.loggedUser.usuario.usuario.trim(),
                password: password.trim()
            }
        }
      };   
      headerRequest =  this.seguridadService.agregarFecha2(headerRequest);   
      return this.seguridadService.getEncrypt2(headerRequest.toString());
    }

    /*Retorna el parametro body encriptado para validacion de credenciales*/
    getBodyValidatedUser(password:string):any{
      const encriptedPassword = this.seguridadService.encriptar(password.trim());
      let loginObject:any = {
        usuario:{
          usuario: this.resourcesService.loggedUser.usuario.usuario.trim(),
          password:encriptedPassword
        }      
      };
      return loginObject;
    }

    /*Obtiene un esdtado de tarea por su id*/
    obtenerEstado(idestado:number):string{
      let estado = "";
      this.listaEstados.forEach((estadoObj: EstadoInterface) => {
        if(estadoObj.idestado == idestado){
          estado = estadoObj.descripcion;
        }
      });
      return estado;
    }

}
