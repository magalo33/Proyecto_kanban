import { Injectable } from '@angular/core';
import { environment } from './../../environments/environment';
import { UsuarioInterface } from '../../interfaces/usuario.interface';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { UsuarioResponseInterface } from 'src/interfaces/usuarioresponse.interface';
import {  EstadoInterface } from '../../interfaces/estado.interface';


const wsUrl = environment.wsUrl;

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  private token: string;  

  constructor(private http: HttpClient) {
    this.token = "";
  }

  get headers() {
    return {
      headers: {
        'X-SESSION-TOKEN': this.token
      }
    };
  }

  public getUsuario(item: string): Observable<UsuarioInterface> {    
    const url = `${wsUrl}usuario/`.concat(item);
    return this.http.get<UsuarioInterface>(url);
  }

  public getEstado(): Observable<EstadoInterface[]> {    
    const url = `${wsUrl}estado`;
    return this.http.get<EstadoInterface[]>(url);
  }

  public postLogin(usuarioBody: UsuarioInterface, token: string): Observable<UsuarioResponseInterface> {
    this.token = token;
    const url = `${wsUrl}/usuario/login`;
    return this.http.post<UsuarioResponseInterface>(url, usuarioBody, this.headers);
  }

  public registrarUsuario(usuarioBody: any, token: string): Observable<UsuarioResponseInterface> {
    this.token = token;
    const url = `${wsUrl}/usuario`;
    return this.http.post<UsuarioResponseInterface>(url, usuarioBody, this.headers);
  }

  public postTarea(tareaBody: any, token: string): Observable<UsuarioResponseInterface> {
    this.token = token;
    const url = `${wsUrl}tarea`;
    return this.http.post<UsuarioResponseInterface>(url, tareaBody, this.headers);
  }

  public putTarea(tareaBody: any, token: string): Observable<UsuarioResponseInterface> {
    this.token = token;
    const url = `${wsUrl}tarea`;
    return this.http.put<UsuarioResponseInterface>(url, tareaBody, this.headers);
  }

  public deleteTarea(token: string): Observable<any> {
        this.token = token;
    const url = `${wsUrl}tarea`;
    return this.http.delete<UsuarioResponseInterface>(url, this.headers);
  }

  public putComentarioportarea(tareaBody: any, token: string): Observable<UsuarioResponseInterface> {
    this.token = token;
    const url = `${wsUrl}comentarioportarea`;
    return this.http.put<UsuarioResponseInterface>(url, tareaBody, this.headers);
  }

}
