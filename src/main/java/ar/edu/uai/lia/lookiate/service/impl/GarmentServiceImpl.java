package ar.edu.uai.lia.lookiate.service.impl;

import ar.edu.uai.lia.lookiate.client.AIClient;
import ar.edu.uai.lia.lookiate.model.Garment;
import ar.edu.uai.lia.lookiate.model.Outfit;
import ar.edu.uai.lia.lookiate.model.Tag;
import ar.edu.uai.lia.lookiate.repository.GarmentRepository;
import ar.edu.uai.lia.lookiate.repository.OutfitRepository;
import ar.edu.uai.lia.lookiate.service.GarmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class GarmentServiceImpl implements GarmentService {

    private final GarmentRepository garmentRepository;
    private final OutfitRepository outfitRepository;
    private final AIClient aiClient;

    public GarmentServiceImpl(GarmentRepository garmentRepository, OutfitRepository outfitRepository, AIClient aiClient) {
        this.garmentRepository = garmentRepository;
        this.outfitRepository = outfitRepository;
        this.aiClient = aiClient;
    }

    @Override
    @Transactional
    public Garment createGarment(Garment garment) {
        if (garment.getGarmentId() == null) {
            garment.setGarmentId(UUID.randomUUID().toString());
        }
        garment.setCreatedAt(new Date());
        garment.setTags(generateTags(garment.getGarmentImg()));
        return garmentRepository.save(garment);
    }

    @Override
    @Transactional
    public void updateImage(String garmentId, String newImage) {
        Garment garment = getGarmentOrThrow(garmentId);
        garment.updateImage(newImage);
        garment.setTags(generateTags(newImage));
        garmentRepository.save(garment);
    }

    @Override
    @Transactional
    public void updateTags(String garmentId, List<Tag> newTags) {
        Garment garment = getGarmentOrThrow(garmentId);
        newTags.forEach(tag -> {
            if (tag.getTagId() == null) {
                tag.setTagId(UUID.randomUUID().toString());
            }
            tag.setGarment(null);
        });
        garment.updateTags(newTags);
        garmentRepository.save(garment);
    }

    @Override
    public Garment getGarmentDetails(String garmentId) {
        return getGarmentOrThrow(garmentId);
    }

    @Override
    public List<Garment> getUserGarments(String userId) {
        // STUB: requiere asociación User↔Wardrobe en el modelo (pendiente, ver PLAN_PENDIENTE.txt)
        return List.of();
    }

    @Override
    @Transactional
    public void delete(String garmentId) {
        getGarmentOrThrow(garmentId);
        for (Outfit outfit : outfitRepository.findByGarment(garmentId)) {
            outfit.getGarments().removeIf(g -> g.getGarmentId().equals(garmentId));
            outfitRepository.save(outfit);
        }
        garmentRepository.delete(garmentId);
    }

    private List<Tag> generateTags(String image) {
        List<Tag> tags = aiClient.generateTags(image);
        tags.forEach(tag -> {
            if (tag.getTagId() == null) {
                tag.setTagId(UUID.randomUUID().toString());
            }
            tag.setGarment(null);
        });
        return tags;
    }

    private Garment getGarmentOrThrow(String garmentId) {
        return garmentRepository.findById(garmentId)
                .orElseThrow(() -> new RuntimeException("Garment not found: " + garmentId));
    }
}
