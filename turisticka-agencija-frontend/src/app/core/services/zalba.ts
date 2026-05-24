import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Zalba {
  id?: number;
  naslov: string;
  opis: string;
  tipZalbe: string;
  status?: string;
  idTure: number;
  putnikId: number;
  hitna?: boolean;
  dodeljeniTim?: string;
  dokumentacijaUrl?: string;
  opisResenja?: string;
  preduzeteMere?: string;
  kontaktiranaStrana?: string;
  alternativnoResenje?: string;
  najduziKorak?: string;
  ocena?: number;
  komentarOcene?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ZalbaService {
  private apiUrl = 'http://localhost:8080/api/zalbe';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Zalba[]> {
    return this.http.get<Zalba[]>(this.apiUrl);
  }

  getNove(): Observable<Zalba[]> {
    return this.http.get<Zalba[]>(`${this.apiUrl}/nove`);
  }

  getByPutnik(putnikId: number): Observable<Zalba[]> {
    return this.http.get<Zalba[]>(`${this.apiUrl}/putnik/${putnikId}`);
  }

  getByTim(tim: string): Observable<Zalba[]> {
    return this.http.get<Zalba[]>(`${this.apiUrl}/tim/${tim}`);
  }

  create(zalba: Zalba): Observable<Zalba> {
    return this.http.post<Zalba>(this.apiUrl, zalba);
  }

  kategorizuj(id: number, tipZalbe: string, hitna: boolean): Observable<Zalba> {
    return this.http.put<Zalba>(
      `${this.apiUrl}/${id}/kategorizuj?tipZalbe=${tipZalbe}&hitna=${hitna}`,
      {}
    );
  }

  dodeliTimu(id: number, timZalbe: string, hitna: boolean): Observable<Zalba> {
    return this.http.put<Zalba>(
      `${this.apiUrl}/${id}/dodeli-timu?timZalbe=${timZalbe}&hitna=${hitna}`,
      {}
    );
  }

  unesiResenje(
    id: number,
    opisResenja: string,
    preduzeteMere: string,
    kontaktiranaStrana: string,
    alternativnoResenje: string,
    najduziKorak: string
  ): Observable<Zalba> {
    const params =
      `opisResenja=${encodeURIComponent(opisResenja)}` +
      `&preduzeteMere=${encodeURIComponent(preduzeteMere)}` +
      `&kontaktiranaStrana=${encodeURIComponent(kontaktiranaStrana)}` +
      `&alternativnoResenje=${encodeURIComponent(alternativnoResenje)}` +
      `&najduziKorak=${encodeURIComponent(najduziKorak)}`;

    return this.http.put<Zalba>(`${this.apiUrl}/${id}/resenje?${params}`, {});
  }

  zatvori(id: number): Observable<Zalba> {
    return this.http.put<Zalba>(`${this.apiUrl}/${id}/zatvori`, {});
  }

  oceni(id: number, ocena: number, komentarOcene: string): Observable<Zalba> {
    return this.http.put<Zalba>(
      `${this.apiUrl}/${id}/oceni?ocena=${ocena}&komentarOcene=${encodeURIComponent(komentarOcene)}`,
      {}
    );
  }

  statistikaPoStatusu(): Observable<any> {
    return this.http.get(`${this.apiUrl}/statistika/broj-po-statusu`);
  }

  statistikaPoTimu(): Observable<any> {
    return this.http.get(`${this.apiUrl}/statistika/broj-po-timu`);
  }

  prosecnaOcena(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/statistika/prosecna-ocena`);
  }

  zalbeKojeKasne(): Observable<Zalba[]> {
    return this.http.get<Zalba[]>(`${this.apiUrl}/statistika/zalbe-koje-kasne`);
  }
}
