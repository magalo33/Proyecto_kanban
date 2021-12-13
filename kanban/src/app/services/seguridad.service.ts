import { Injectable } from '@angular/core';
import { UUID } from 'angular2-uuid';
import * as CryptoJS from 'crypto-js';
import * as moment from 'moment-timezone';


@Injectable({
  providedIn: 'root'
})


export class SeguridadService {

  private key = '1WR3AqfY33J@5yavQklFkLDmpb6YQY0o';

  constructor() { }

  public getToken(): string {
    return UUID.UUID();
  }

  public agregarFecha(body: any): any {
    body.expdate = '\''+moment().tz('America/Bogota').format('YYYY-MM-DD HH:mm:ss')+'\'';
    body = JSON.stringify(body);
    return body;
  }


  public agregarFecha2(body: any): any {
    body.expdate = moment().tz('America/Bogota').format('YYYY-MM-DD HH:mm:ss');
    body = JSON.stringify(body);
    return body;
  }

  public getEncrypt(body: any): string {
    var jsonTostr = JSON.stringify(body);
    jsonTostr = jsonTostr.replace(/\"/g, "");
    jsonTostr = jsonTostr.replace(/\\/g, "");
    return this.encriptar(jsonTostr);
  }


  public getEncrypt2(body: string): string {
    return this.encriptar(body);
  }

  public encriptar(dato:string): string {
    var pwhash = CryptoJS.SHA1(CryptoJS.enc.Utf8.parse(this.key));
    var key = CryptoJS.enc.Hex.parse(pwhash.toString(CryptoJS.enc.Hex).substr(0, 32));
    var encrypted = CryptoJS.AES.encrypt(dato, key, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    var ciphertext = encrypted.ciphertext.toString(CryptoJS.enc.Hex);
    return ciphertext;
  }



  public getFecha(): string {
    return moment().tz('America/Bogota').format('YYYY-MM-DD');
  }

}
