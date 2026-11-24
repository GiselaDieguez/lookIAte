package ar.edu.uai.lia.lookiate.service.impl;

import ar.edu.uai.lia.lookiate.model.Garment;
import ar.edu.uai.lia.lookiate.model.Wardrobe;
import ar.edu.uai.lia.lookiate.repository.GarmentRepository;
import ar.edu.uai.lia.lookiate.repository.WardrobeRepository;
import ar.edu.uai.lia.lookiate.service.WardrobeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WardrobeServiceImpl implements WardrobeService {

    private final WardrobeRepository wardrobeRepository;
    private final GarmentRepository garmentRepository;

    public WardrobeServiceImpl(WardrobeRepository wardrobeRepository, GarmentRepository garmentRepository) {
        this.wardrobeRepository = wardrobeRepository;
        this.garmentRepository = garmentRepository;
    }

    @Override
    @Transactional
    public Wardrobe createWardrobe(Wardrobe wardrobe) {
        if (wardrobe.getWardrobeId() == null) {
            wardrobe.setWardrobeId(java.util.UUID.randomUUID().toString());
        }
        return wardrobeRepository.save(wardrobe);
    }

    @Override
    public List<Garment> listGarments(String wardrobeId) {
        return garmentRepository.findByWardrobe(wardrobeId);
    }

    @Override
    @Transactional
    public void addGarment(String wardrobeId, String garmentId) {
        Wardrobe wardrobe = getWardrobeOrThrow(wardrobeId);
        Garment garment = getGarmentOrThrow(garmentId);
        wardrobe.addGarment(garment);
        garment.setWardrobe(wardrobe);
        wardrobeRepository.save(wardrobe);
        garmentRepository.save(garment);
    }

    @Override
    @Transactional
    public void removeGarment(String wardrobeId, String garmentId) {
        Wardrobe wardrobe = getWardrobeOrThrow(wardrobeId);
        Garment garment = getGarmentOrThrow(garmentId);
        wardrobe.removeGarment(garmentId);
        garment.setWardrobe(null);
        wardrobeRepository.save(wardrobe);
        garmentRepository.save(garment);
    }

    @Override
    public Wardrobe getDetails(String wardrobeId) {
        return getWardrobeOrThrow(wardrobeId);
    }

    private Wardrobe getWardrobeOrThrow(String wardrobeId) {
        return wardrobeRepository.findById(wardrobeId)
                .orElseThrow(() -> new RuntimeException("Wardrobe not found: " + wardrobeId));
    }

    private Garment getGarmentOrThrow(String garmentId) {
        return garmentRepository.findById(garmentId)
                .orElseThrow(() -> new RuntimeException("Garment not found: " + garmentId));
    }
}
