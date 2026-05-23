package com.example.turisticka_agencija.controller;

import com.example.turisticka_agencija.model.*;
import com.example.turisticka_agencija.service.ZalbaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/zalbe")
@CrossOrigin(origins = "*")
public class ZalbaController {

    private final ZalbaService zalbaService;

    public ZalbaController(ZalbaService zalbaService) {
        this.zalbaService = zalbaService;
    }

    @PostMapping
    public Zalba kreirajZalbu(@RequestBody Zalba zalba) {
        return zalbaService.kreirajZalbu(zalba);
    }

    @GetMapping
    public List<Zalba> pronadjiSve() {
        return zalbaService.pronadjiSve();
    }

    @GetMapping("/{id}")
    public Zalba pronadjiPoId(@PathVariable Long id) {
        return zalbaService.pronadjiPoId(id);
    }

    @GetMapping("/putnik/{putnikId}")
    public List<Zalba> pronadjiPoPutniku(@PathVariable Long putnikId) {
        return zalbaService.pronadjiPoPutniku(putnikId);
    }

    @GetMapping("/nove")
    public List<Zalba> pronadjiNoveZalbe() {
        return zalbaService.pronadjiNoveZalbe();
    }

    @GetMapping("/hitne")
    public List<Zalba> pronadjiHitne() {
        return zalbaService.pronadjiHitne();
    }

    @GetMapping("/status/{status}")
    public List<Zalba> pronadjiPoStatusu(@PathVariable StatusZalbe status) {
        return zalbaService.pronadjiPoStatusu(status);
    }

    @GetMapping("/tim/{timZalbe}")
    public List<Zalba> pronadjiPoTimu(@PathVariable TimZalbe timZalbe) {
        return zalbaService.pronadjiPoTimu(timZalbe);
    }

    @PutMapping("/{id}")
    public Zalba izmeniZalbu(@PathVariable Long id, @RequestBody Zalba zalba) {
        return zalbaService.izmeniZalbu(id, zalba);
    }

    @DeleteMapping("/{id}")
    public void obrisiZalbu(@PathVariable Long id) {
        zalbaService.obrisiZalbu(id);
    }

    @PostMapping("/{id}/dokumentacija")
    public Zalba dodajDokumentaciju(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        return zalbaService.dodajDokumentaciju(id, file);
    }

    @PutMapping("/{id}/kategorizuj")
    public Zalba kategorizujZalbu(
            @PathVariable Long id,
            @RequestParam TipZalbe tipZalbe,
            @RequestParam(defaultValue = "false") boolean hitna
    ) {
        return zalbaService.kategorizujZalbu(id, tipZalbe, hitna);
    }

    @PutMapping("/{id}/dodeli-timu")
    public Zalba dodeliTimu(
            @PathVariable Long id,
            @RequestParam TimZalbe timZalbe,
            @RequestParam(defaultValue = "false") boolean hitna
    ) {
        return zalbaService.dodeliTimu(id, timZalbe, hitna);
    }

    @PutMapping("/{id}/automatski-dodeli")
    public Zalba automatskiDodeliPoPravilu(@PathVariable Long id) {
        return zalbaService.automatskiDodeliPoPravilu(id);
    }

    @PutMapping("/{id}/ceka-odgovor")
    public Zalba oznaciDaCekaOdgovor(@PathVariable Long id) {
        return zalbaService.oznaciDaCekaOdgovor(id);
    }

    @PutMapping("/{id}/resenje")
    public Zalba unesiResenje(
            @PathVariable Long id,
            @RequestParam String opisResenja,
            @RequestParam(required = false) String preduzeteMere,
            @RequestParam(required = false) String kontaktiranaStrana,
            @RequestParam(required = false) String alternativnoResenje,
            @RequestParam(required = false) String najduziKorak
    ) {
        return zalbaService.unesiResenje(
                id,
                opisResenja,
                preduzeteMere,
                kontaktiranaStrana,
                alternativnoResenje,
                najduziKorak
        );
    }

    @PutMapping("/{id}/zatvori")
    public Zalba zatvoriZalbu(@PathVariable Long id) {
        return zalbaService.zatvoriZalbu(id);
    }

    @PutMapping("/{id}/oceni")
    public Zalba oceniZalbu(
            @PathVariable Long id,
            @RequestParam Integer ocena,
            @RequestParam(required = false) String komentarOcene
    ) {
        return zalbaService.oceniZalbu(id, ocena, komentarOcene);
    }

    @PutMapping("/{id}/smestaj/kontaktiraj-vlasnika")
    public Zalba kontaktirajVlasnikaSmestaja(
            @PathVariable Long id,
            @RequestParam String vlasnikSmestaja
    ) {
        return zalbaService.evidentirajAkcijuTima(
                id,
                "Kontaktiran vlasnik smeštaja.",
                vlasnikSmestaja,
                null,
                "Čekanje odgovora vlasnika smeštaja",
                "Tim za smeštaj je kontaktirao vlasnika smeštaja."
        );
    }

    @PutMapping("/{id}/smestaj/alternativni-smestaj")
    public Zalba alternativniSmestaj(
            @PathVariable Long id,
            @RequestParam String alternativniSmestaj
    ) {
        return zalbaService.evidentirajAkcijuTima(
                id,
                "Pronađen alternativni smeštaj.",
                null,
                alternativniSmestaj,
                "Pronalazak alternativnog smeštaja",
                "Putniku je ponuđen alternativni smeštaj."
        );
    }

    @PutMapping("/{id}/prevoz/kontaktiraj-prevoznika")
    public Zalba kontaktirajPrevoznika(
            @PathVariable Long id,
            @RequestParam String prevoznik
    ) {
        return zalbaService.evidentirajAkcijuTima(
                id,
                "Kontaktiran prevoznik.",
                prevoznik,
                null,
                "Čekanje odgovora prevoznika",
                "Tim za prevoz je kontaktirao prevoznika."
        );
    }

    @PutMapping("/{id}/prevoz/alternativni-prevoz")
    public Zalba alternativniPrevoz(
            @PathVariable Long id,
            @RequestParam String alternativniPrevoz
    ) {
        return zalbaService.evidentirajAkcijuTima(
                id,
                "Organizovan alternativni prevoz.",
                null,
                alternativniPrevoz,
                "Organizacija alternativnog prevoza",
                "Putniku je ponuđen alternativni prevoz."
        );
    }

    @PutMapping("/{id}/dokumentacija/provera-podataka")
    public Zalba proveraPodataka(
            @PathVariable Long id,
            @RequestParam String rezultatProvere
    ) {
        return zalbaService.evidentirajAkcijuTima(
                id,
                "Provereni podaci putnika.",
                null,
                null,
                "Provera dokumentacije",
                rezultatProvere
        );
    }

    @PutMapping("/{id}/dokumentacija/ispravka-podataka")
    public Zalba ispravkaPodataka(
            @PathVariable Long id,
            @RequestParam String opisIspravke
    ) {
        return zalbaService.evidentirajAkcijuTima(
                id,
                "Izvršena ispravka podataka.",
                "Administrativni sektor / nadležna institucija",
                null,
                "Ispravka grešaka u podacima",
                opisIspravke
        );
    }

    @PutMapping("/{id}/dokumentacija/kontaktiraj-instituciju")
    public Zalba kontaktirajInstituciju(
            @PathVariable Long id,
            @RequestParam String institucija
    ) {
        return zalbaService.evidentirajAkcijuTima(
                id,
                "Kontaktirana nadležna institucija.",
                institucija,
                null,
                "Koordinacija sa nadležnim institucijama",
                "Tim za dokumentaciju je kontaktirao instituciju."
        );
    }

    @PutMapping("/{id}/ostalo/utvrdi-prirodu-problema")
    public Zalba utvrdiPriroduProblema(
            @PathVariable Long id,
            @RequestParam String prirodaProblema
    ) {
        return zalbaService.evidentirajAkcijuTima(
                id,
                "Utvrđena priroda problema.",
                null,
                null,
                "Analiza nestandardne žalbe",
                prirodaProblema
        );
    }

    @PutMapping("/{id}/ostalo/kontaktiraj-odgovornu-stranu")
    public Zalba kontaktirajOdgovornuStranu(
            @PathVariable Long id,
            @RequestParam String odgovornaStrana
    ) {
        return zalbaService.evidentirajAkcijuTima(
                id,
                "Kontaktirana odgovorna strana.",
                odgovornaStrana,
                null,
                "Kontaktiranje odgovornih strana",
                "Tim za ostale žalbe je kontaktirao odgovornu stranu."
        );
    }

    @GetMapping("/obavestenja/uloga/{uloga}")
    public List<Obavestenje> obavestenjaZaUlogu(@PathVariable String uloga) {
        return zalbaService.obavestenjaZaUlogu(uloga);
    }

    @GetMapping("/obavestenja/{uloga}/{korisnikId}")
    public List<Obavestenje> obavestenjaZaKorisnika(
            @PathVariable String uloga,
            @PathVariable Long korisnikId
    ) {
        return zalbaService.obavestenjaZaKorisnika(uloga, korisnikId);
    }

    @GetMapping("/{id}/obavestenja")
    public List<Obavestenje> obavestenjaZaZalbu(@PathVariable Long id) {
        return zalbaService.obavestenjaZaZalbu(id);
    }

    @GetMapping("/statistika/broj-po-statusu")
    public Map<String, Long> brojZalbiPoStatusu() {
        return zalbaService.brojZalbiPoStatusu();
    }

    @GetMapping("/statistika/broj-po-timu")
    public Map<String, Long> brojZalbiPoTimu() {
        return zalbaService.brojZalbiPoTimu();
    }

    @GetMapping("/statistika/broj-po-tipu")
    public Map<String, Long> brojZalbiPoTipu() {
        return zalbaService.brojZalbiPoTipu();
    }

    @GetMapping("/statistika/prosecna-ocena")
    public Double prosecnaOcena() {
        return zalbaService.prosecnaOcena();
    }

    @GetMapping("/statistika/prosecno-vreme-resavanja")
    public Double prosecnoVremeResavanjaUSatima() {
        return zalbaService.prosecnoVremeResavanjaUSatima();
    }

    @GetMapping("/statistika/zalbe-koje-kasne")
    public List<Zalba> zalbeKojeKasne() {
        return zalbaService.zalbeKojeKasne();
    }

    @GetMapping("/statistika/najproblematicniji-koraci")
    public Map<String, Long> najproblematicnijiKoraci() {
        return zalbaService.najproblematicnijiKoraci();
    }
}