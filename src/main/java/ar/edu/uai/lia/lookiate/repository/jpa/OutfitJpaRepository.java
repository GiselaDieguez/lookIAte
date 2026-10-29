package ar.edu.uai.lia.lookiate.repository.jpa;

import ar.edu.uai.lia.lookiate.model.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutfitJpaRepository extends JpaRepository<Outfit, String> {
    List<Outfit> findByIsFavoriteTrue();
    List<Outfit> findByUserId(String userId);
    List<Outfit> findByOccasion(String occasion);
    List<Outfit> findByVisibility(Boolean visibility);
}
