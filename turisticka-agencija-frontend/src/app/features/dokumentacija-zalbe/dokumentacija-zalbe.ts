import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import {
  DokumentacijaZalbe,
  DokumentacijaZalbeService,
} from '../../core/services/dokumentacija-zalbe';

@Component({
  selector: 'app-dokumentacija-zalbe',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dokumentacija-zalbe.html',
  styleUrl: './dokumentacija-zalbe.css',
})
export class DokumentacijaZalbeComponent implements OnInit {
  zalbaId: number | null = null;
  dokumentacija: DokumentacijaZalbe | null = null;

  loading = false;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private dokumentacijaZalbeService: DokumentacijaZalbeService,
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('zalbaId');

    if (!id) {
      this.errorMessage = 'Nije pronađen ID žalbe.';
      return;
    }

    this.zalbaId = Number(id);
    this.loadDokumentacija();
  }

  loadDokumentacija(): void {
    if (!this.zalbaId) {
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.dokumentacijaZalbeService.getDokumentacijaZaZalbu(this.zalbaId).subscribe({
      next: (data: DokumentacijaZalbe) => {
        this.dokumentacija = data;
        this.loading = false;
      },
      error: (err: unknown) => {
        console.error(err);
        this.loading = false;
        this.errorMessage = 'Dokumentacija za ovu žalbu nije pronađena.';
      },
    });
  }

  nazad(): void {
    this.router.navigate(['/zalbe']);
  }

  sacuvajKaoPdf(): void {
    window.print();
  }
}
