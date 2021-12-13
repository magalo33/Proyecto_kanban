import { ComentariosportareaInterface } from './comentariosportarea.interface';
import { EstadoInterface } from './estado.interface';
export interface TareaInterface{
    idtarea: number,
    descripcion:string
    comentariosportareasList: ComentariosportareaInterface[]
    estado:EstadoInterface
    idusuario:number
}