package com.example.turisticka_agencija.service;

import com.example.turisticka_agencija.dto.AdditionalActivityRequest;
import com.example.turisticka_agencija.dto.AdditionalActivityResponse;
import com.example.turisticka_agencija.exception.BadRequestException;
import com.example.turisticka_agencija.model.AdditionalActivity;
import com.example.turisticka_agencija.model.Role;
import com.example.turisticka_agencija.model.User;
import com.example.turisticka_agencija.repository.AdditionalActivityRepository;
import com.example.turisticka_agencija.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
public class AdditionalActivityService {

    private final AdditionalActivityRepository additionalActivityRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    private static final Path UPLOAD_DIR = Paths.get(
            System.getProperty("user.dir"),
            "uploads",
            "additional-activities"
    );

    public AdditionalActivityService(
            AdditionalActivityRepository additionalActivityRepository,
            UserRepository userRepository,
            ObjectMapper objectMapper
    ) {
        this.additionalActivityRepository = additionalActivityRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    public List<AdditionalActivityResponse> getAllAdditionalActivities() {
        return additionalActivityRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public AdditionalActivityResponse getAdditionalActivityById(Long id) {
        AdditionalActivity activity = findActivityById(id);
        return mapToResponse(activity);
    }

    public AdditionalActivityResponse createAdditionalActivity(
            String infoJson,
            MultipartFile image,
            Principal principal
    ) {
        try {
            User user = getAuthenticatedUser(principal);
            validateManager(user);

            AdditionalActivityRequest request =
                    objectMapper.readValue(infoJson, AdditionalActivityRequest.class);

            validateRequiredFields(request);

            AdditionalActivity activity = new AdditionalActivity();

            activity.setName(request.getName().trim());
            activity.setDescription(request.getDescription().trim());
            activity.setPrice(request.getPrice());
            activity.setDurationMinutes(request.getDurationMinutes());
            activity.setLocation(request.getLocation().trim());
            activity.setImageUrl(saveImage(image));
            activity.setCreatedBy(user);

            return mapToResponse(additionalActivityRepository.save(activity));

        } catch (BadRequestException e) {
            throw e;
        }  catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException(e);
    }
    }

    public AdditionalActivityResponse updateAdditionalActivity(
            Long id,
            String infoJson,
            MultipartFile image,
            Principal principal
    ) {
        try {
            User user = getAuthenticatedUser(principal);
            validateManager(user);

            AdditionalActivity activity = findActivityById(id);

            if (activity.getCreatedBy() == null) {
                throw new BadRequestException("This activity does not have a creator");
            }

            if (!activity.getCreatedBy().getId().equals(user.getId())) {
                throw new BadRequestException("You can only update your own activities");
            }

            AdditionalActivityRequest request =
                    objectMapper.readValue(infoJson, AdditionalActivityRequest.class);

            if (request.getName() != null) {
                if (request.getName().isBlank()) {
                    throw new BadRequestException("Name cannot be empty");
                }
                activity.setName(request.getName().trim());
            }

            if (request.getDescription() != null) {
                if (request.getDescription().isBlank()) {
                    throw new BadRequestException("Description cannot be empty");
                }
                activity.setDescription(request.getDescription().trim());
            }

            if (request.getPrice() != null) {
                if (request.getPrice() < 0) {
                    throw new BadRequestException("Price cannot be negative");
                }
                activity.setPrice(request.getPrice());
            }

            if (request.getDurationMinutes() != null) {
                if (request.getDurationMinutes() <= 0) {
                    throw new BadRequestException("Duration must be greater than 0");
                }
                activity.setDurationMinutes(request.getDurationMinutes());
            }

            if (request.getLocation() != null) {
                if (request.getLocation().isBlank()) {
                    throw new BadRequestException("Location cannot be empty");
                }
                activity.setLocation(request.getLocation().trim());
            }

            if (image != null && !image.isEmpty()) {
                activity.setImageUrl(saveImage(image));
            }

            return mapToResponse(additionalActivityRepository.save(activity));

        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Greška prilikom izmene aktivnosti: " + e.getMessage());
        }
    }

    private String saveImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            throw new BadRequestException("Image is required");
        }

        String originalFilename = image.getOriginalFilename();

        if (originalFilename == null || originalFilename.isBlank()) {
            throw new BadRequestException("Image name is invalid");
        }

        String extension = StringUtils.getFilenameExtension(originalFilename);

        if (extension == null || extension.isBlank()) {
            throw new BadRequestException("Image extension is missing");
        }

        extension = extension.toLowerCase();

        if (extension.equals("jfif")) {
            extension = "jpg";
        }

        if (!extension.equals("jpg")
                && !extension.equals("jpeg")
                && !extension.equals("png")
                && !extension.equals("webp")) {
            throw new BadRequestException("Only JPG, JPEG, PNG and WEBP images are allowed");
        }

        String fileName = UUID.randomUUID() + "." + extension;

        try {
            if (!Files.exists(UPLOAD_DIR)) {
                Files.createDirectories(UPLOAD_DIR);
            }

            Path filePath = UPLOAD_DIR.resolve(fileName);

            Files.copy(
                    image.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return "/uploads/additional-activities/" + fileName;

        } catch (IOException e) {
            throw new BadRequestException("Image could not be saved");
        }
    }

    public void deleteAdditionalActivity(Long id, Principal principal) {
        User user = getAuthenticatedUser(principal);

        validateManager(user);

        AdditionalActivity activity = findActivityById(id);

        if (activity.getCreatedBy() == null) {
            throw new BadRequestException("This activity does not have a creator");
        }

        if (!activity.getCreatedBy().getId().equals(user.getId())) {
            throw new BadRequestException("You can only delete your own activities");
        }

        additionalActivityRepository.delete(activity);
    }

    private AdditionalActivity findActivityById(Long id) {
        return additionalActivityRepository.findById(id)
                .orElseThrow(() ->
                        new BadRequestException(
                                "Additional activity with id " + id + " was not found"
                        )
                );
    }

    private User getAuthenticatedUser(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new BadRequestException("User is not authenticated");
        }

        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() ->
                        new BadRequestException("Authenticated user not found")
                );
    }

    private void validateManager(User user) {
        if (user.getRole() != Role.MANAGER) {
            throw new BadRequestException(
                    "Only users with MANAGER role can manage additional activities"
            );
        }
    }

    private void validateRequiredFields(AdditionalActivityRequest request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new BadRequestException("Name is required");
        }

        if (request.getDescription() == null || request.getDescription().isBlank()) {
            throw new BadRequestException("Description is required");
        }

        if (request.getPrice() == null) {
            throw new BadRequestException("Price is required");
        }

        if (request.getPrice() < 0) {
            throw new BadRequestException("Price cannot be negative");
        }

        if (request.getDurationMinutes() == null) {
            throw new BadRequestException("Duration is required");
        }

        if (request.getDurationMinutes() <= 0) {
            throw new BadRequestException("Duration must be greater than 0");
        }

        if (request.getLocation() == null || request.getLocation().isBlank()) {
            throw new BadRequestException("Location is required");
        }
    }

    private AdditionalActivityResponse mapToResponse(AdditionalActivity activity) {
        Long createdById = null;
        String createdByUsername = null;

        if (activity.getCreatedBy() != null) {
            createdById = activity.getCreatedBy().getId();
            createdByUsername = activity.getCreatedBy().getUsername();
        }

        return new AdditionalActivityResponse(
                activity.getId(),
                activity.getName(),
                activity.getDescription(),
                activity.getPrice(),
                activity.getDurationMinutes(),
                activity.getLocation(),
                activity.getImageUrl(),
                createdById,
                createdByUsername
        );
    }
}