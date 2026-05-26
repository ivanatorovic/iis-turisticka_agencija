import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface DokumentacijaZalbe {
  zalbaId: number;
  naslovZalbe: string;
  opisZalbe: string;
  tipZalbe: string;
  statusZalbe: string;
  hitna: boolean;

  arrangementId: number;
  nazivAranzmana: string;
  opisAranzmana: string;
  osnovnaCena: number;
  brojNocenja: number;

  destinacijaNaziv: string;
  destinacijaDrzava: string;

  nazivSmestaja: string;
  kategorijaSmestaja: string;

  tipPrevoza: string;
  kompanijaPrevoza: string;

  putnikId: number;
  imePutnika: string;
  prezimePutnika: string;
  usernamePutnika: string;
  emailPutnika: string;
  kontaktPutnika: string;

  kontaktSmestaja: string;
  emailSmestaja: string;
  odgovornaOsobaSmestaja: string;

  kontaktPrevoznika: string;
  emailPrevoznika: string;
  odgovornaOsobaPrevoznika: string;

  kontaktHitno: string;
  planPuta: string;
  napomeneZaTim: string;
}

@Injectable({
  providedIn: 'root',
})
export class DokumentacijaZalbeService {
  private apiUrl = 'http://localhost:8080/api/zalbe';

  constructor(private http: HttpClient) {}

  getDokumentacijaZaZalbu(zalbaId: number): Observable<DokumentacijaZalbe> {
    return this.http.get<DokumentacijaZalbe>(`${this.apiUrl}/${zalbaId}/dokumentacija`);
  }
}
