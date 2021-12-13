import { UsuarioxRolInterface } from "./usuarioxrol.interface";
import { TareaInterface } from './tarea.interface';

export interface UsuarioInterface{
    idusuario: number,
    usuario: string,
    password: string,
    nombre: string,
    usuarioxrolesList: UsuarioxRolInterface[],
    tareasList:TareaInterface[]
}