package ar.edu.uai.lia.lookiate.service;

import ar.edu.uai.lia.lookiate.model.Garment;
import ar.edu.uai.lia.lookiate.model.Tag;

import java.util.List;

public interface GarmentService {
    Garment createGarment(Garment garment);
    void updateImage(String garmentId, String newImage);
    void updateTags(String garmentId, List<Tag> newTags);
    Garment getGarmentDetails(String garmentId);
    List<Garment> getUserGarments(String userId);
    void delete(String garmentId);
}
