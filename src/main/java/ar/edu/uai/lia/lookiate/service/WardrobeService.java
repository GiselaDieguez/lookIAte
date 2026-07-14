package ar.edu.uai.lia.lookiate.service;

import ar.edu.uai.lia.lookiate.model.Garment;
import ar.edu.uai.lia.lookiate.model.Wardrobe;

import java.util.List;

public interface WardrobeService {
    Wardrobe createWardrobe(Wardrobe wardrobe);
    List<Garment> listGarments(Long wardrobeId);
    void addGarment(Long wardrobeId, Long garmentId);
    void removeGarment(Long wardrobeId, Long garmentId);
    Wardrobe getDetails(Long wardrobeId);
}
