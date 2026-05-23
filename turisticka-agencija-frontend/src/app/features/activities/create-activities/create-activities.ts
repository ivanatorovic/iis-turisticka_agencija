import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';
import {
  AdditionalActivityRequest,
  AdditionalActivityService,
} from '../../../core/services/additional.activity';

@Component({
  selector: 'app-create-activities',
  imports: [CommonModule, FormsModule, RouterLink, SidebarMenu],
  templateUrl: './create-activities.html',
  styleUrl: './create-activities.css',
})
export class CreateActivities implements OnInit {
  activityId: number | null = null;
  isEditMode = false;

  loading = false;
  saving = false;
  errorMessage = '';
  successMessage = '';

  selectedImage: File | null = null;

  form: AdditionalActivityRequest = {
    name: '',
    description: '',
    price: null as any,
    durationMinutes: null as any,
    location: '',
    imageUrl: '',
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private additionalActivityService: AdditionalActivityService,
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.activityId = Number(id);
      this.isEditMode = true;
      this.loadActivity(this.activityId);
    }
  }

  loadActivity(id: number): void {
    this.loading = true;

    this.additionalActivityService.getById(id).subscribe({
      next: (activity) => {
        this.form = {
          name: activity.name,
          description: activity.description,
          price: activity.price,
          durationMinutes: activity.durationMinutes,
          location: activity.location,
          imageUrl: activity.imageUrl,
        };

        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = err?.error?.message || 'Nije moguće učitati podatke o aktivnosti.';
        this.loading = false;
      },
    });
  }

  saveActivity(): void {
    this.errorMessage = '';
    this.successMessage = '';

    if (!this.isFormValid()) {
      return;
    }

    this.saving = true;

    const formData = this.buildFormData();

    if (this.isEditMode && this.activityId !== null) {
      this.additionalActivityService.update(this.activityId, formData).subscribe({
        next: () => {
          this.saving = false;
          this.successMessage = 'Aktivnost je uspešno izmenjena.';

          setTimeout(() => {
            this.router.navigate(['/additional-activities-manager']);
          }, 800);
        },
        error: (err) => {
          this.saving = false;
          this.errorMessage =
            err?.error?.message || 'Došlo je do greške prilikom izmene aktivnosti.';
        },
      });

      return;
    }

    this.additionalActivityService.create(formData).subscribe({
      next: () => {
        this.saving = false;
        this.successMessage = 'Aktivnost je uspešno kreirana.';

        setTimeout(() => {
          this.router.navigate(['/additional-activities-manager']);
        }, 800);
      },
      error: (err) => {
        this.saving = false;
        this.errorMessage =
          err?.error?.message || 'Došlo je do greške prilikom kreiranja aktivnosti.';
      },
    });
  }

  onImageSelected(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (!input.files || input.files.length === 0) {
      return;
    }

    this.selectedImage = input.files[0];
  }

  buildFormData(): FormData {
    const info = {
      name: this.form.name,
      description: this.form.description,
      price: this.form.price,
      durationMinutes: this.form.durationMinutes,
      location: this.form.location,
    };

    const formData = new FormData();

    formData.append('info', JSON.stringify(info));

    if (this.selectedImage) {
      formData.append('image', this.selectedImage);
    }

    return formData;
  }

  isFormValid(): boolean {
    if (!this.form.name.trim()) {
      this.errorMessage = 'Naziv aktivnosti je obavezan.';
      return false;
    }

    if (!this.form.description.trim()) {
      this.errorMessage = 'Opis aktivnosti je obavezan.';
      return false;
    }

    if (this.form.price === null || this.form.price === undefined) {
      this.errorMessage = 'Cena je obavezna.';
      return false;
    }

    if (this.form.price < 0) {
      this.errorMessage = 'Cena ne može biti negativna.';
      return false;
    }

    if (this.form.durationMinutes === null || this.form.durationMinutes === undefined) {
      this.errorMessage = 'Trajanje je obavezno.';
      return false;
    }

    if (this.form.durationMinutes <= 0) {
      this.errorMessage = 'Trajanje mora biti veće od 0.';
      return false;
    }

    if (!this.form.location.trim()) {
      this.errorMessage = 'Lokacija je obavezna.';
      return false;
    }

    if (!this.isEditMode && !this.selectedImage) {
      this.errorMessage = 'Slika aktivnosti je obavezna.';
      return false;
    }

    return true;
  }
}
