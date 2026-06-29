package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class SalesAnalyticsPdfService {

    private final SalesAnalyticsService salesAnalyticsService;

    public SalesAnalyticsPdfService(SalesAnalyticsService salesAnalyticsService) {
        this.salesAnalyticsService = salesAnalyticsService;
    }

    public byte[] generateSalesAnalyticsPdf(int year, Long arrangementId) {
        System.out.println("USAO U PDF SERVICE");

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(document, outputStream);

            document.open();

            BaseFont baseFont = BaseFont.createFont(
                    BaseFont.HELVETICA,
                    BaseFont.CP1250,
                    BaseFont.EMBEDDED
            );

            Font titleFont = new Font(baseFont, 22, Font.BOLD);
            Font sectionFont = new Font(baseFont, 15, Font.BOLD);
            Font normalFont = new Font(baseFont, 11, Font.NORMAL);
            Font headerFont = new Font(baseFont, 11, Font.BOLD);

            Paragraph title = new Paragraph("Izveštaj analitike prodaje", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(18);
            document.add(title);

            document.add(new Paragraph("Godina: " + year, normalFont));
            document.add(new Paragraph("Datum generisanja: " + LocalDate.now(), normalFont));
            document.add(Chunk.NEWLINE);

            YearlySalesSummaryDto summary = salesAnalyticsService.getYearlySummary(year);
            List<YearRevenueDto> revenueByYears = salesAnalyticsService.getRevenueByYears();
            List<PopularDestinationDto> destinations = salesAnalyticsService.getPopularDestinations(year);
            List<PopularArrangementDto> arrangements = salesAnalyticsService.getPopularArrangements(year);

            List<MonthlyArrangementSalesDto> monthly =
                    arrangementId != null
                            ? salesAnalyticsService.getMonthlyArrangementSales(arrangementId, year)
                            : List.of();

            addSection(document, "Godišnji pregled", sectionFont);

            PdfPTable summaryTable = new PdfPTable(2);
            summaryTable.setWidthPercentage(100);

            addRow(summaryTable, normalFont, "Ukupno rezervacija", String.valueOf(summary.getTotalReservations()));
            addRow(summaryTable, normalFont, "Potvrđene rezervacije", String.valueOf(summary.getConfirmedReservations()));
            addRow(summaryTable, normalFont, "Otkazane rezervacije", String.valueOf(summary.getCancelledReservations()));
            addRow(summaryTable, normalFont, "Ukupan prihod", formatMoney(summary.getTotalRevenue()));
            addRow(summaryTable, normalFont, "Prosečna vrednost rezervacije", formatMoney(summary.getAverageReservationValue()));

            document.add(summaryTable);
            document.add(Chunk.NEWLINE);

            addSection(document, "Prihod po godinama", sectionFont);

            PdfPTable revenueTable = new PdfPTable(3);
            revenueTable.setWidthPercentage(100);
            addHeader(revenueTable, headerFont, "Godina", "Broj rezervacija", "Prihod");

            for (YearRevenueDto item : revenueByYears) {
                addRow(revenueTable, normalFont,
                        String.valueOf(item.getYear()),
                        String.valueOf(item.getReservationCount()),
                        formatMoney(item.getRevenue()));
            }

            document.add(revenueTable);
            document.add(Chunk.NEWLINE);

            addSection(document, "Popularne destinacije", sectionFont);

            PdfPTable destinationTable = new PdfPTable(4);
            destinationTable.setWidthPercentage(100);
            addHeader(destinationTable, headerFont, "Destinacija", "Država", "Rezervacije", "Prihod");

            for (PopularDestinationDto item : destinations) {
                addRow(destinationTable, normalFont,
                        item.getDestinationName(),
                        item.getCountry(),
                        String.valueOf(item.getReservationCount()),
                        formatMoney(item.getRevenue()));
            }

            document.add(destinationTable);
            document.add(Chunk.NEWLINE);

            addSection(document, "Najuspešniji aranžmani", sectionFont);

            PdfPTable arrangementTable = new PdfPTable(3);
            arrangementTable.setWidthPercentage(100);
            addHeader(arrangementTable, headerFont, "Aranžman", "Rezervacije", "Prihod");

            for (PopularArrangementDto item : arrangements) {
                addRow(arrangementTable, normalFont,
                        item.getArrangementName(),
                        String.valueOf(item.getReservationCount()),
                        formatMoney(item.getRevenue()));
            }

            document.add(arrangementTable);
            document.add(Chunk.NEWLINE);

            if (!monthly.isEmpty()) {
                addSection(document, "Sezonalnost izabranog aranžmana", sectionFont);

                PdfPTable monthlyTable = new PdfPTable(3);
                monthlyTable.setWidthPercentage(100);
                addHeader(monthlyTable, headerFont, "Mesec", "Rezervacije", "Prihod");

                for (MonthlyArrangementSalesDto item : monthly) {
                    addRow(monthlyTable, normalFont,
                            item.getMonthName(),
                            String.valueOf(item.getReservationCount()),
                            formatMoney(item.getRevenue()));
                }

                document.add(monthlyTable);
            }

            document.close();

            byte[] pdfBytes = outputStream.toByteArray();

            System.out.println("VELICINA PDF U SERVISU: " + pdfBytes.length);

            return pdfBytes;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom generisanja PDF izveštaja.", e);
        }
    }

    private void addSection(Document document, String title, Font font) throws DocumentException {
        Paragraph paragraph = new Paragraph(title, font);
        paragraph.setSpacingBefore(12);
        paragraph.setSpacingAfter(8);
        document.add(paragraph);
    }

    private void addHeader(PdfPTable table, Font font, String... values) {
        for (String value : values) {
            PdfPCell cell = new PdfPCell(new Phrase(value, font));
            cell.setBackgroundColor(new Color(216, 193, 155));
            cell.setPadding(6);
            table.addCell(cell);
        }
    }

    private void addRow(PdfPTable table, Font font, String... values) {
        for (String value : values) {
            PdfPCell cell = new PdfPCell(new Phrase(value, font));
            cell.setPadding(6);
            table.addCell(cell);
        }
    }

    private String formatMoney(double value) {
        return String.format("%.2f EUR", value);
    }
}