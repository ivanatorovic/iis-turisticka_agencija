package com.turisticka_agencija.dodatne_aktivnosti.service.impl;

import com.turisticka_agencija.dodatne_aktivnosti.dto.AdditionalActivityExecutionDTO;
import com.turisticka_agencija.dodatne_aktivnosti.model.AdditionalActivity;
import com.turisticka_agencija.dodatne_aktivnosti.model.AdditionalActivityExecution;
import com.turisticka_agencija.dodatne_aktivnosti.model.Arrangement;
import com.turisticka_agencija.dodatne_aktivnosti.repository.AdditionalActivityExecutionRepository;
import com.turisticka_agencija.dodatne_aktivnosti.repository.AdditionalActivityRepository;
import com.turisticka_agencija.dodatne_aktivnosti.repository.ArrangementRepository;
import com.turisticka_agencija.dodatne_aktivnosti.service.IAdditionalActivityExecutionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdditionalActivityExecutionService implements IAdditionalActivityExecutionService {

    private final AdditionalActivityExecutionRepository executionRepository;
    private final AdditionalActivityRepository activityRepository;
    private final ArrangementRepository arrangementRepository;

    public AdditionalActivityExecutionService(
            AdditionalActivityExecutionRepository executionRepository,
            AdditionalActivityRepository activityRepository,
            ArrangementRepository arrangementRepository
    ) {
        this.executionRepository = executionRepository;
        this.activityRepository = activityRepository;
        this.arrangementRepository = arrangementRepository;
    }

    @Override
    public List<AdditionalActivityExecution> findAll() {
        return executionRepository.findAll();
    }

    @Override
    public AdditionalActivityExecution findById(Long id) {
        return executionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Execution not found with id: " + id));
    }

    @Override
    public AdditionalActivityExecution save(AdditionalActivityExecutionDTO dto) {
        AdditionalActivityExecution execution = new AdditionalActivityExecution();

        execution.setExecutionId(dto.getExecutionId());
        applyDto(execution, dto);

        return executionRepository.save(execution);
    }

    @Override
    public AdditionalActivityExecution update(Long id, AdditionalActivityExecutionDTO dto) {
        AdditionalActivityExecution execution = findById(id);

        applyDto(execution, dto);

        return executionRepository.save(execution);
    }

    @Override
    public void delete(Long id) {
        executionRepository.deleteById(id);
    }

    private void applyDto(AdditionalActivityExecution execution, AdditionalActivityExecutionDTO dto) {
        if (dto.getActivityDate() != null) {
            execution.setActivityDate(dto.getActivityDate());
        }

        if (dto.getStartTime() != null) {
            execution.setStartTime(dto.getStartTime());
        }

        if (dto.getDurationMinutes() != null) {
            execution.setDurationMinutes(dto.getDurationMinutes());
        }

        if (dto.getCapacity() != null) {
            execution.setCapacity(dto.getCapacity());
        }

        if (dto.getReservedSpots() != null) {
            execution.setReservedSpots(dto.getReservedSpots());
        }

        if (dto.getPrice() != null) {
            execution.setPrice(dto.getPrice());
        }

        if (dto.getStatus() != null) {
            execution.setStatus(dto.getStatus());
        }

        if (dto.getPrior() != null) {
            execution.setPrior(dto.getPrior());
        }

        if (dto.getActivityId() != null) {
            AdditionalActivity activity = activityRepository.findById(dto.getActivityId())
                    .orElseThrow(() -> new RuntimeException("Activity not found with id: " + dto.getActivityId()));

            execution.setActivity(activity);
        }

        if (dto.getArrangementId() != null) {
            Arrangement arrangement = arrangementRepository.findById(dto.getArrangementId())
                    .orElseThrow(() -> new RuntimeException("Arrangement not found with id: " + dto.getArrangementId()));

            execution.setArrangement(arrangement);
        }
    }
}