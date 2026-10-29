package ar.edu.uai.lia.lookiate.service.impl;

import ar.edu.uai.lia.lookiate.client.AIClient;
import ar.edu.uai.lia.lookiate.model.Garment;
import ar.edu.uai.lia.lookiate.model.Tag;
import ar.edu.uai.lia.lookiate.repository.GarmentRepository;
import ar.edu.uai.lia.lookiate.service.GarmentService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class GarmentServiceImpl implements GarmentService {

    private final GarmentRepository garmentRepository;
    private final AIClient aiClient;

    public GarmentServiceImpl(GarmentRepository garmentRepository, AIClient aiClient) {
        this.garmentRepository = garmentRepository;
        this.aiClient = aiClient;
    }

    @Override
    public Garment createGarment(Garment garment) {
        garment.setCreatedAt(new Date());
        garment.setTags(aiClient.generateTags(garment.getGarmentImg()));
        return garmentRepository.save(garment);
    }

    @Override
    public void updateImage(String garmentId, String newImage) {
        Garment garment = getGarmentOrThrow(garmentId);
        garment.updateImage(newImage);
        garment.setTags(aiClient.generateTags(newImage));
        garmentRepository.save(garment);
    }

    @Override
    public void updateTags(String garmentId, List<Tag> newTags) {
        Garment garment = getGarmentOrThrow(garmentId);
        garment.updateTags(newTags);
        garmentRepository.save(garment);
    }

    @Override
    public Garment getGarmentDetails(String garmentId) {
        return getGarmentOrThrow(garmentId);
    }

    @Override
    public List<Garment> getUserGarments(String userId) {
        return List.of();
    }

    @Override
    public void delete(String garmentId) {
        getGarmentOrThrow(garmentId);
        garmentRepository.delete(garmentId);
    }

    private Garment getGarmentOrThrow(String garmentId) {
        return garmentRepository.findById(garmentId)
                .orElseThrow(() -> new RuntimeException("Garment not found: " + garmentId));
    }
}
