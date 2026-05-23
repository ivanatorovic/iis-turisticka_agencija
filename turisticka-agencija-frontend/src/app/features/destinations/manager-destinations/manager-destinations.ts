import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DestinationService } from '../../../core/services/destination';
import { SidebarMenu } from '../../../layout/sidebar-menu/sidebar-menu';

@Component({
  selector: 'app-manager-destinations',
  standalone: true,
  imports: [CommonModule, FormsModule, SidebarMenu],
  templateUrl: './manager-destinations.html',
  styleUrls: ['./manager-destinations.css']
})
export class ManagerDestinations implements OnInit {

  destinations: any[] = [];

  expandedCategories: string[] = [];
  expandedCountries: string[] = [];

  newCategory = '';

  selectedCategoryForCountry = '';
  newCountry = '';
  firstCityForCountry = '';
  firstCityDescription = '';

  selectedCategoryForCity = '';
  selectedCountryForCity = '';
  newCity = '';
  newCityDescription = '';

  constructor(private destinationService: DestinationService) {}

  ngOnInit(): void {
    this.loadDestinations();
  }

  loadDestinations(): void {
    this.destinationService.getAll().subscribe({
      next: (data: any[]) => {
        this.destinations = data;
      },
      error: (err: any) => console.error(err)
    });
  }

  getCategories(): string[] {
    return [...new Set(this.destinations.map(d => d.category))];
  }

  getCountries(category: string): string[] {
    return [...new Set(
      this.destinations
        .filter(d => d.category === category)
        .map(d => d.country)
    )];
  }

  getCities(category: string, country: string): any[] {
    return this.destinations.filter(
      d => d.category === category && d.country === country
    );
  }

  toggleCategory(category: string): void {
    if (this.expandedCategories.includes(category)) {
      this.expandedCategories = this.expandedCategories.filter(c => c !== category);
    } else {
      this.expandedCategories.push(category);
    }
  }

  toggleCountry(category: string, country: string): void {
    const key = `${category}-${country}`;

    if (this.expandedCountries.includes(key)) {
      this.expandedCountries = this.expandedCountries.filter(c => c !== key);
    } else {
      this.expandedCountries.push(key);
    }
  }

  isCategoryExpanded(category: string): boolean {
    return this.expandedCategories.includes(category);
  }

  isCountryExpanded(category: string, country: string): boolean {
    return this.expandedCountries.includes(`${category}-${country}`);
  }

  addCategory(): void {
    if (!this.newCategory.trim()) {
      alert('Unesite naziv kategorije.');
      return;
    }

    if (this.getCategories().includes(this.newCategory.trim())) {
      alert('Kategorija već postoji.');
      return;
    }

    this.selectedCategoryForCountry = this.newCategory.trim();
    this.expandedCategories.push(this.newCategory.trim());

    alert('Kategorija je dodata u prikaz. Da bi bila sačuvana u bazi, dodajte državu i grad.');

    this.newCategory = '';
  }

  addCountry(): void {
    if (!this.selectedCategoryForCountry || !this.newCountry || !this.firstCityForCountry) {
      alert('Izaberite kategoriju i unesite državu i prvi grad.');
      return;
    }

    const destination = {
      category: this.selectedCategoryForCountry,
      country: this.newCountry,
      name: this.firstCityForCountry,
      description: this.firstCityDescription
    };

    this.destinationService.create(destination).subscribe({
      next: () => {
        this.clearCountryForm();
        this.loadDestinations();
      },
      error: (err: any) => console.error(err)
    });
  }

  addCity(): void {
    if (!this.selectedCategoryForCity || !this.selectedCountryForCity || !this.newCity) {
      alert('Izaberite kategoriju i državu i unesite grad.');
      return;
    }

    const destination = {
      category: this.selectedCategoryForCity,
      country: this.selectedCountryForCity,
      name: this.newCity,
      description: this.newCityDescription
    };

    this.destinationService.create(destination).subscribe({
      next: () => {
        this.clearCityForm();
        this.loadDestinations();
      },
      error: (err: any) => console.error(err)
    });
  }

  deleteCity(id: number): void {
    if (!confirm('Da li želite da obrišete ovaj grad?')) {
      return;
    }

    this.destinationService.delete(id).subscribe({
      next: () => this.loadDestinations(),
      error: (err: any) => console.error(err)
    });
  }

  deleteCountry(category: string, country: string): void {
    if (!confirm(`Da li želite da obrišete državu ${country}?`)) {
      return;
    }

    const cities = this.getCities(category, country);

    cities.forEach(city => {
      this.destinationService.delete(city.id).subscribe({
        next: () => this.loadDestinations(),
        error: (err: any) => console.error(err)
      });
    });
  }

  deleteCategory(category: string): void {
    if (!confirm(`Da li želite da obrišete kategoriju ${category}?`)) {
      return;
    }

    const items = this.destinations.filter(d => d.category === category);

    items.forEach(item => {
      this.destinationService.delete(item.id).subscribe({
        next: () => this.loadDestinations(),
        error: (err: any) => console.error(err)
      });
    });
  }

  clearCountryForm(): void {
    this.selectedCategoryForCountry = '';
    this.newCountry = '';
    this.firstCityForCountry = '';
    this.firstCityDescription = '';
  }

  clearCityForm(): void {
    this.selectedCategoryForCity = '';
    this.selectedCountryForCity = '';
    this.newCity = '';
    this.newCityDescription = '';
  }
}