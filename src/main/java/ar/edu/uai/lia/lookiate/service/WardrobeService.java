package ar.edu.uai.lia.lookiate.service;

import ar.edu.uai.lia.lookiate.model.Garment;
import ar.edu.uai.lia.lookiate.model.Wardrobe;

import java.util.List;

public interface WardrobeService {
    Wardrobe createWardrobe(Wardrobe wardrobe);
    List<Garment> listGarments(String wardrobeId);
    void addGarment(String wardrobeId, String garmentId);
    void removeGarment(String wardrobeId, String garmentId);
    Wardrobe getDetails(String wardrobeId);
}
