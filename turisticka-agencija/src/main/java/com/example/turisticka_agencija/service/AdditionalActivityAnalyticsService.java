package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.*;
import com.example.turisticka_agencija.exception.BadRequestException;
import com.example.turisticka_agencija.model.Role;
import com.example.turisticka_agencija.model.User;
import com.example.turisticka_agencija.repository.AdditionalActivityAnalyticsRepository;
import com.example.turisticka_agencija.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import java.io.ByteArrayOutputStream;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdditionalActivityAnalyticsService {

    private final AdditionalActivityAnalyticsRepository analyticsRepository;
    private final UserRepository userRepository;

    public AdditionalActivityAnalyticsService(
            AdditionalActivityAnalyticsRepository analyticsRepository,
            UserRepository userRepository
    ) {
        this.analyticsRepository = analyticsRepository;
        this.userRepository = userRepository;
    }

    public AdditionalActivityAnalyticsResponse getAnalytics(
            Long arrangementId,
            Long arrangementTermId,
            Principal principal
    ) {
        validateManager(principal);

        if (arrangementId == null) {
            throw new BadRequestException("Aranžman je obavezan za izveštaj");
        }

        var rows = analyticsRepository.getAnalyticsReport(
                arrangementId,
                arrangementTermId
        );

        List<AdditionalActivityAnalyticsRowDto> tableRows =
                rows.stream()
                        .map(row ->
                                new AdditionalActivityAnalyticsRowDto(
                                        row.getArrangementId(),
                                        row.getArrangementName(),
                                        row.getArrangementTermId(),
                                        row.getTermStartDate(),
                                        row.getTermEndDate(),
                                        row.getExecutionId(),
                                        row.getActivityName(),
                                        row.getActivityDate(),
                                        row.getGuideName(),
                                        row.getRegistrationsCount(),
                                        row.getParticipantsCount(),
                                        row.getCapacity(),
                                        row.getReservedSpots(),
                                        row.getOccupancyRate(),
                                        row.getRevenue(),
                                        row.getCancelRate()
                                )
                        )
                        .toList();

        return new AdditionalActivityAnalyticsResponse(
                buildSummary(tableRows),
                buildPopularity(tableRows),
                buildOccupancy(tableRows),
                buildRevenue(tableRows),
                buildCancellations(tableRows),
                buildGuideWorkload(tableRows),
                tableRows
        );
    }

    private AdditionalActivityAnalyticsSummaryDto buildSummary(
            List<AdditionalActivityAnalyticsRowDto> rows
    ) {
        long totalRegistrations = rows.stream()
                .mapToLong(AdditionalActivityAnalyticsRowDto::getRegistrationsCount)
                .sum();

        long totalParticipants = rows.stream()
                .mapToLong(AdditionalActivityAnalyticsRowDto::getParticipantsCount)
                .sum();

        double totalRevenue = rows.stream()
                .mapToDouble(AdditionalActivityAnalyticsRowDto::getRevenue)
                .sum();

        double averageOccupancy = rows.isEmpty()
                ? 0
                : rows.stream()
                .mapToDouble(AdditionalActivityAnalyticsRowDto::getOccupancyRate)
                .average()
                .orElse(0);

        return new AdditionalActivityAnalyticsSummaryDto(
                totalRegistrations,
                totalParticipants,
                round(totalRevenue),
                round(averageOccupancy)
        );
    }

    private List<ActivityPopularityDto> buildPopularity(
            List<AdditionalActivityAnalyticsRowDto> rows
    ) {
        Map<String, List<AdditionalActivityAnalyticsRowDto>> grouped =
                rows.stream().collect(Collectors.groupingBy(
                        AdditionalActivityAnalyticsRowDto::getActivityName
                ));

        return grouped.entrySet()
                .stream()
                .map(entry -> new ActivityPopularityDto(
                        entry.getKey(),
                        entry.getValue().stream()
                                .mapToLong(AdditionalActivityAnalyticsRowDto::getRegistrationsCount)
                                .sum(),
                        entry.getValue().stream()
                                .mapToLong(AdditionalActivityAnalyticsRowDto::getParticipantsCount)
                                .sum()
                ))
                .sorted(Comparator.comparingLong(ActivityPopularityDto::getParticipantsCount).reversed())
                .toList();
    }

    private List<ActivityOccupancyDto> buildOccupancy(
            List<AdditionalActivityAnalyticsRowDto> rows
    ) {
        return rows.stream()
                .map(row -> new ActivityOccupancyDto(
                        row.getActivityName(),
                        row.getCapacity(),
                        row.getReservedSpots(),
                        round(row.getOccupancyRate())
                ))
                .sorted(Comparator.comparingDouble(ActivityOccupancyDto::getOccupancyRate).reversed())
                .toList();
    }

    private List<ActivityRevenueDto> buildRevenue(
            List<AdditionalActivityAnalyticsRowDto> rows
    ) {
        Map<String, Double> grouped = rows.stream()
                .collect(Collectors.groupingBy(
                        AdditionalActivityAnalyticsRowDto::getActivityName,
                        Collectors.summingDouble(AdditionalActivityAnalyticsRowDto::getRevenue)
                ));

        return grouped.entrySet()
                .stream()
                .map(entry -> new ActivityRevenueDto(entry.getKey(), round(entry.getValue())))
                .sorted(Comparator.comparingDouble(ActivityRevenueDto::getRevenue).reversed())
                .toList();
    }

    private List<ActivityCancellationDto> buildCancellations(
            List<AdditionalActivityAnalyticsRowDto> rows
    ) {
        return rows.stream()
                .map(row -> new ActivityCancellationDto(
                        row.getActivityName(),
                        round(row.getCancelRate())
                ))
                .sorted(Comparator.comparingDouble(ActivityCancellationDto::getCancelRate).reversed())
                .toList();
    }

    private List<GuideWorkloadDto> buildGuideWorkload(
            List<AdditionalActivityAnalyticsRowDto> rows
    ) {
        Map<String, List<AdditionalActivityAnalyticsRowDto>> grouped =
                rows.stream().collect(Collectors.groupingBy(
                        AdditionalActivityAnalyticsRowDto::getGuideName
                ));

        return grouped.entrySet()
                .stream()
                .map(entry -> new GuideWorkloadDto(
                        entry.getKey(),
                        entry.getValue().size(),
                        entry.getValue().stream()
                                .mapToLong(AdditionalActivityAnalyticsRowDto::getParticipantsCount)
                                .sum()
                ))
                .sorted(Comparator.comparingLong(GuideWorkloadDto::getParticipantsCount).reversed())
                .toList();
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private void validateManager(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new BadRequestException("Korisnik nije prijavljen");
        }

        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new BadRequestException("Korisnik nije pronađen"));

        if (user.getRole() != Role.MANAGER) {
            throw new BadRequestException("Samo menadžer može da vidi analitiku dodatnih aktivnosti");
        }
    }

    private PdfPCell createCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text == null ? "" : text, font));
        cell.setPadding(4);
        cell.setBorderWidth(0.4f);
        return cell;
    }

    public byte[] generatePdfReport(
            Long arrangementId,
            Long arrangementTermId,
            Principal principal
    ) {
        AdditionalActivityAnalyticsResponse report = getAnalytics(
                arrangementId,
                arrangementTermId,
                principal
        );

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            Document document = new Document(PageSize.A4, 30, 30, 36, 36);
            PdfWriter.getInstance(document, outputStream);

            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
            Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 7);

            Paragraph title = new Paragraph("Izvestaj dodatnih aktivnosti", titleFont);
            title.setSpacingAfter(14);
            document.add(title);

            var summary = report.getSummary();

            PdfPTable summaryTable = new PdfPTable(4);
            summaryTable.setWidthPercentage(100);
            summaryTable.setSpacingAfter(18);

            summaryTable.addCell(createCell("Ukupno prijava", headerFont));
            summaryTable.addCell(createCell("Ukupno ucesnika", headerFont));
            summaryTable.addCell(createCell("Ukupan prihod", headerFont));
            summaryTable.addCell(createCell("Prosecna popunjenost", headerFont));

            summaryTable.addCell(createCell(String.valueOf(summary.getTotalRegistrations()), normalFont));
            summaryTable.addCell(createCell(String.valueOf(summary.getTotalParticipants()), normalFont));
            summaryTable.addCell(createCell(String.format("%.2f EUR", summary.getTotalRevenue()), normalFont));
            summaryTable.addCell(createCell(summary.getAverageOccupancy() + "%", normalFont));

            document.add(summaryTable);

            Map<Long, List<AdditionalActivityAnalyticsRowDto>> rowsByTerm =
                    report.getTableRows()
                            .stream()
                            .collect(Collectors.groupingBy(
                                    AdditionalActivityAnalyticsRowDto::getArrangementTermId,
                                    LinkedHashMap::new,
                                    Collectors.toList()
                            ));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

            for (List<AdditionalActivityAnalyticsRowDto> termRows : rowsByTerm.values()) {
                if (termRows.isEmpty()) {
                    continue;
                }

                AdditionalActivityAnalyticsRowDto firstRow = termRows.get(0);

                Paragraph termTitle = new Paragraph(
                        "Termin aranzmana: "
                                + firstRow.getTermStartDate().format(formatter)
                                + " - "
                                + firstRow.getTermEndDate().format(formatter),
                        subtitleFont
                );
                termTitle.setSpacingBefore(8);
                termTitle.setSpacingAfter(4);
                document.add(termTitle);

                Paragraph arrangementName = new Paragraph(
                        "Aranzman: " + firstRow.getArrangementName(),
                        smallFont
                );
                arrangementName.setSpacingAfter(8);
                document.add(arrangementName);

                PdfPTable activitiesTable = new PdfPTable(7);
                activitiesTable.setWidthPercentage(100);
                activitiesTable.setWidths(new float[]{
                        3.8f,
                        1.35f,
                        0.75f,
                        0.75f,
                        1.15f,
                        0.9f,
                        0.9f
                });
                activitiesTable.setSpacingAfter(14);

                activitiesTable.addCell(createCell("Aktivnost / vodic", headerFont));
                activitiesTable.addCell(createCell("Datum", headerFont));
                activitiesTable.addCell(createCell("Prij.", headerFont));
                activitiesTable.addCell(createCell("Uc.", headerFont));
                activitiesTable.addCell(createCell("Prihod", headerFont));
                activitiesTable.addCell(createCell("Pop.%", headerFont));
                activitiesTable.addCell(createCell("Otk.%", headerFont));

                for (AdditionalActivityAnalyticsRowDto row : termRows) {
                    String activityAndGuide =
                            row.getActivityName()
                                    + "\nVodic: "
                                    + (row.getGuideName() == null ? "Nema vodica" : row.getGuideName());

                    activitiesTable.addCell(createCell(activityAndGuide, normalFont));
                    activitiesTable.addCell(createCell(row.getActivityDate().format(formatter), normalFont));
                    activitiesTable.addCell(createCell(String.valueOf(row.getRegistrationsCount()), normalFont));
                    activitiesTable.addCell(createCell(String.valueOf(row.getParticipantsCount()), normalFont));
                    activitiesTable.addCell(createCell(String.format("%.2f EUR", row.getRevenue()), normalFont));
                    activitiesTable.addCell(createCell(row.getOccupancyRate() + "%", normalFont));
                    activitiesTable.addCell(createCell(row.getCancelRate() + "%", normalFont));
                }

                document.add(activitiesTable);
            }

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new BadRequestException("Generisanje PDF izvestaja nije uspelo");
        }
    }
}