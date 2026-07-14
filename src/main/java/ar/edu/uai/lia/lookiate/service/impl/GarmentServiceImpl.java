package ar.edu.uai.lia.lookiate.service.impl;

import ar.edu.uai.lia.lookiate.model.Garment;
import ar.edu.uai.lia.lookiate.model.Tag;
import ar.edu.uai.lia.lookiate.repository.GarmentRepository;
import ar.edu.uai.lia.lookiate.repository.Repository;
import ar.edu.uai.lia.lookiate.service.GarmentService;

import java.util.Date;
import java.util.List;

public class GarmentServiceImpl implements GarmentService {

    private final GarmentRepository garmentRepository;
    private final Repository repository;

    // Inyección de dependencias falta agregar AIImageProcessor que vendrá de otro proyecto.
    public GarmentServiceImpl(GarmentRepository garmentRepository, Repository repository) {
        this.garmentRepository = garmentRepository;
        this.repository = repository;
    }

    @Override
    public Garment createGarment(Garment garment) {
        // Procesa la imagen con IA y genera los tags automáticamente
        // Agregar procesamiento de tags, nivel de confidencia
        garment.setCreatedAt(new Date());
        return garmentRepository.save(garment);
    }

    @Override
    public void updateImage(String garmentId, String newImage) {
        Garment garment = garmentRepository.findById(garmentId)
                .orElseThrow(() -> new RuntimeException("Garment not found: " + garmentId));
        // Reprocesa la nueva imagen con IA
        // Agregar procesamiento de tags, nivel de confidencia
        garment.setGarmentImg(newImage);
        garmentRepository.save(garment);
    }

    @Override
    public void updateTags(String garmentId, List<Tag> newTags) {
        Garment garment = garmentRepository.findById(garmentId)
                .orElseThrow(() -> new RuntimeException("Garment not found: " + garmentId));
        garment.setTags(newTags);
        garmentRepository.save(garment);
    }

    @Override
    public Garment getGarmentDetails(String garmentId) {
        return garmentRepository.findById(garmentId)
                .orElseThrow(() -> new RuntimeException("Garment not found: " + garmentId));
    }

    public void delete(String garmentId) {
        if (garmentRepository.findById(garmentId).isEmpty()) {
            throw new RuntimeException("Garment not found: " + garmentId);
        }
        garmentRepository.delete(garmentId);
    }

}
