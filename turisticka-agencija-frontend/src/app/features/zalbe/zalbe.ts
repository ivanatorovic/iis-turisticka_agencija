import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Zalba, ZalbaService } from '../../core/services/zalba';
import { SidebarMenu } from '../../layout/sidebar-menu/sidebar-menu';
@Component({
  selector: 'app-zalbe',
  standalone: true,
  imports: [CommonModule, FormsModule,SidebarMenu],
  templateUrl: './zalbe.html',
  styleUrl: './zalbe.css',
})
export class Zalbe implements OnInit {
  role = '';
  username = '';
  userId: number | null = null;

  idTureFromReservation: number | null = null;
  reservationIdFromUrl: number | null = null;

  zalbe: Zalba[] = [];
  statistikaStatusi: any = null;
  statistikaTimovi: any = null;
  prosecnaOcena = 0;

  errorMessage = '';
  successMessage = '';

  novaZalba: Zalba = {
    naslov: '',
    opis: '',
    tipZalbe: 'SMESTAJ',
    idTure: 1,
    putnikId: 1,
    nazivTure: '',
  };

  selectedTip = 'SMESTAJ';
  selectedTim = 'TIM_ZA_SMESTAJ';
  selectedHitna = false;

  opisResenja = '';
  preduzeteMere = '';
  kontaktiranaStrana = '';
  alternativnoResenje = '';
  najduziKorak = '';

  ocena = 5;
  komentarOcene = '';

  constructor(
    private zalbaService: ZalbaService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.loadLoggedUser();
    this.loadQueryParams();
    this.loadDataByRole();
  }

  loadLoggedUser(): void {
    this.role = localStorage.getItem('role') || '';
    this.username = localStorage.getItem('username') || '';

    const storedId =
      localStorage.getItem('userId') ||
      localStorage.getItem('id');

    this.userId = storedId ? Number(storedId) : null;

    if (this.userId) {
      this.novaZalba.putnikId = this.userId;
    }
  }

  loadQueryParams(): void {
    this.route.queryParams.subscribe((params) => {
      const idTure = params['idTure'];
      const reservationId = params['reservationId'];
      const nazivTure = params['nazivTure'];

      if (idTure) {
        this.idTureFromReservation = Number(idTure);
        this.novaZalba.idTure = Number(idTure);
      }

      if (reservationId) {
        this.reservationIdFromUrl = Number(reservationId);
        this.novaZalba.reservationId = Number(reservationId);
      }

      if (nazivTure) {
        this.novaZalba.nazivTure = nazivTure;
      }
    });
  }


  loadDataByRole(): void {
    this.clearMessages();

    if (this.role === 'CUSTOMER') {
      this.loadPutnikZalbe();
      return;
    }

    if (this.role === 'COMPLAINT_OPERATOR') {
      this.loadNoveZalbe();
      return;
    }

    if (this.isComplaintTeam()) {
      this.loadZalbeZaTim();
      return;
    }

    if (this.role === 'COMPLAINT_MANAGER' || this.role === 'DIRECTOR') {
      this.loadMenadzerData();
      return;
    }

    if (this.role === 'ADMIN') {
      this.loadSveZalbe();
      return;
    }

    this.loadSveZalbe();
  }

  isComplaintTeam(): boolean {
    return this.role === 'COMPLAINT_TEAM_ACCOMMODATION'
      || this.role === 'COMPLAINT_TEAM_TRANSPORT'
      || this.role === 'COMPLAINT_TEAM_DOCUMENTATION'
      || this.role === 'COMPLAINT_TEAM_OTHER';
  }

  getTimForRole(): string {
    if (this.role === 'COMPLAINT_TEAM_TRANSPORT') {
      return 'TIM_ZA_PREVOZ';
    }

    if (this.role === 'COMPLAINT_TEAM_DOCUMENTATION') {
      return 'TIM_ZA_DOKUMENTACIJU';
    }

    if (this.role === 'COMPLAINT_TEAM_OTHER') {
      return 'TIM_ZA_OSTALO';
    }

    return 'TIM_ZA_SMESTAJ';
  }

  getRoleTitle(): string {
    if (this.role === 'CUSTOMER') {
      return 'Putnik';
    }

    if (this.role === 'COMPLAINT_OPERATOR') {
      return 'Operater žalbi';
    }

    if (this.role === 'COMPLAINT_TEAM_ACCOMMODATION') {
      return 'Tim za smeštaj';
    }

    if (this.role === 'COMPLAINT_TEAM_TRANSPORT') {
      return 'Tim za prevoz';
    }

    if (this.role === 'COMPLAINT_TEAM_DOCUMENTATION') {
      return 'Tim za dokumentaciju';
    }

    if (this.role === 'COMPLAINT_TEAM_OTHER') {
      return 'Tim za ostale žalbe';
    }

    if (this.role === 'COMPLAINT_MANAGER') {
      return 'Menadžer žalbi';
    }

    if (this.role === 'DIRECTOR') {
      return 'Direktor';
    }

    if (this.role === 'ADMIN') {
      return 'Administrator';
    }

    return 'Korisnik';
  }

  loadSveZalbe(): void {
    this.zalbaService.getAll().subscribe({
      next: (data) => this.zalbe = data,
      error: () => this.errorMessage = 'Greška pri učitavanju žalbi.',
    });
  }

  loadNoveZalbe(): void {
    this.zalbaService.getNove().subscribe({
      next: (data) => this.zalbe = data,
      error: () => this.errorMessage = 'Greška pri učitavanju novih žalbi.',
    });
  }

  loadPutnikZalbe(): void {
    if (!this.userId) {
      this.errorMessage = 'Nije pronađen ID putnika.';
      return;
    }

    this.novaZalba.putnikId = this.userId;

    this.zalbaService.getByPutnik(this.userId).subscribe({
      next: (data) => {
        this.zalbe = data;
      },
      error: () => this.errorMessage = 'Greška pri učitavanju žalbi putnika.',
    });
  }

  loadZalbeZaTim(): void {
    const tim = this.getTimForRole();

    this.zalbaService.getByTim(tim).subscribe({
      next: (data) => this.zalbe = data,
      error: () => this.errorMessage = 'Greška pri učitavanju žalbi tima.',
    });
  }

  loadMenadzerData(): void {
    this.zalbaService.statistikaPoStatusu().subscribe({
      next: (data) => this.statistikaStatusi = data,
      error: () => this.errorMessage = 'Greška pri učitavanju statistike po statusu.',
    });

    this.zalbaService.statistikaPoTimu().subscribe({
      next: (data) => this.statistikaTimovi = data,
      error: () => this.errorMessage = 'Greška pri učitavanju statistike po timu.',
    });

    this.zalbaService.prosecnaOcena().subscribe({
      next: (data) => this.prosecnaOcena = data,
      error: () => this.errorMessage = 'Greška pri učitavanju prosečne ocene.',
    });

    this.loadSveZalbe();
  }

  kreirajZalbu(): void {
    this.clearMessages();

    if (!this.novaZalba.naslov || !this.novaZalba.opis) {
      this.errorMessage = 'Naslov i opis su obavezni.';
      return;
    }

    if (!this.userId) {
      this.errorMessage = 'Nije pronađen ID putnika.';
      return;
    }

    if (!this.novaZalba.idTure) {
      this.errorMessage = 'Nije pronađen ID ture za koju se podnosi žalba.';
      return;
    }

    const nazivTureFromUrl = this.route.snapshot.queryParamMap.get('nazivTure');
    const reservationIdFromUrl = this.route.snapshot.queryParamMap.get('reservationId');

    const zalbaZaSlanje: Zalba = {
      ...this.novaZalba,
      putnikId: this.userId,
      reservationId: reservationIdFromUrl ? Number(reservationIdFromUrl) : this.reservationIdFromUrl || undefined,
      nazivTure: nazivTureFromUrl || this.novaZalba.nazivTure || 'Nepoznata tura',
    };

    console.log('SALJEM ZALBU:', zalbaZaSlanje);

    this.zalbaService.create(zalbaZaSlanje).subscribe({
      next: () => {
        this.successMessage = 'Žalba je uspešno podneta.';

        this.novaZalba = {
          naslov: '',
          opis: '',
          tipZalbe: 'SMESTAJ',
          idTure: this.idTureFromReservation || 1,
          reservationId: reservationIdFromUrl ? Number(reservationIdFromUrl) : this.reservationIdFromUrl || undefined,
          putnikId: this.userId || 1,
          nazivTure: nazivTureFromUrl || '',
        };

        this.loadDataByRole();
      },
      error: () => {
        this.errorMessage = 'Greška pri podnošenju žalbe.';
      },
    });
  }

  kategorizuj(zalba: Zalba): void {
    if (!zalba.id) return;

    this.zalbaService.kategorizuj(zalba.id, this.selectedTip, this.selectedHitna).subscribe({
      next: () => {
        this.successMessage = 'Žalba je kategorisana.';
        this.loadDataByRole();
      },
      error: () => this.errorMessage = 'Greška pri kategorizaciji žalbe.',
    });
  }

  dodeliTimu(zalba: Zalba): void {
    if (!zalba.id) return;

    this.zalbaService.dodeliTimu(zalba.id, this.selectedTim, this.selectedHitna).subscribe({
      next: () => {
        this.successMessage = 'Žalba je dodeljena timu.';
        this.loadDataByRole();
      },
      error: () => this.errorMessage = 'Greška pri dodeljivanju žalbe timu.',
    });
  }

  unesiResenje(zalba: Zalba): void {
    if (!zalba.id) return;

    if (!this.opisResenja) {
      this.errorMessage = 'Opis rešenja je obavezan.';
      return;
    }

    this.zalbaService.unesiResenje(
      zalba.id,
      this.opisResenja,
      this.preduzeteMere,
      this.kontaktiranaStrana,
      this.alternativnoResenje,
      this.najduziKorak
    ).subscribe({
      next: () => {
        this.successMessage = 'Rešenje je uspešno uneto.';
        this.opisResenja = '';
        this.preduzeteMere = '';
        this.kontaktiranaStrana = '';
        this.alternativnoResenje = '';
        this.najduziKorak = '';
        this.loadDataByRole();
      },
      error: () => this.errorMessage = 'Greška pri unosu rešenja.',
    });
  }

  zatvori(zalba: Zalba): void {
    if (!zalba.id) return;

    this.zalbaService.zatvori(zalba.id).subscribe({
      next: () => {
        this.successMessage = 'Žalba je zatvorena.';
        this.loadDataByRole();
      },
      error: () => this.errorMessage = 'Greška pri zatvaranju žalbe.',
    });
  }

  oceni(zalba: Zalba): void {
    if (!zalba.id) return;

    this.zalbaService.oceni(zalba.id, this.ocena, this.komentarOcene).subscribe({
      next: () => {
        this.successMessage = 'Žalba je ocenjena.';
        this.komentarOcene = '';
        this.loadDataByRole();
      },
      error: () => this.errorMessage = 'Greška pri ocenjivanju žalbe.',
    });
  }

  mozeDaOceni(zalba: Zalba): boolean {
    return zalba.status === 'ZATVORENO' && !zalba.ocena;
  }

  otvoriDokumentacijuZalbe(zalba: Zalba): void {
    if (!zalba.id) {
      this.errorMessage = 'Nije pronađen ID žalbe.';
      return;
    }

    this.router.navigate(['/dokumentacija-zalbe', zalba.id]);
  }

  clearMessages(): void {
    this.errorMessage = '';
    this.successMessage = '';
  }
  getNazivTure(zalba: Zalba): string {
    if (zalba.nazivTure && zalba.nazivTure !== 'Nepoznata tura') {
      return zalba.nazivTure;
    }

    const naziviTura: { [key: number]: string } = {
      1: 'Rim city break',
      2: 'Letovanje Hurgada',
      3: 'Rim city break',
      4: 'Letovanje Hurgada',
      5: 'Pariz romantično putovanje',
    };

    return naziviTura[zalba.idTure] || 'Nepoznata tura';
  }
}
