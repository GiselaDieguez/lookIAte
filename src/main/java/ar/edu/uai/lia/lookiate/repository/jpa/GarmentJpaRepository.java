package ar.edu.uai.lia.lookiate.repository.jpa;

import ar.edu.uai.lia.lookiate.model.Garment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GarmentJpaRepository extends JpaRepository<Garment, String> {
    List<Garment> findByWardrobeId(String wardrobeId);
    List<Garment> findByTagsTagValue(String tagValue);
    List<Garment> findByTagsTagType(String tagType);
}
