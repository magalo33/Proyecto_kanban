import { UsuarioInterface } from './usuario.interface';
export interface UsuarioResponseInterface{
    error: number,
    descripcion: string,
    usuario: UsuarioInterface
}