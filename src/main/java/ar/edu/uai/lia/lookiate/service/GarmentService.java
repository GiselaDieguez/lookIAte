package ar.edu.uai.lia.lookiate.service;

import ar.edu.uai.lia.lookiate.model.Garment;
import ar.edu.uai.lia.lookiate.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GarmentService {
    Garment createGarment(Garment garment);
    void updateImage(String garmentId, String newImage);
    void updateTags(String garmentId, List<Tag> newTags);
    Garment getGarmentDetails(String garmentId);
    void delete(String garmentId);
}
