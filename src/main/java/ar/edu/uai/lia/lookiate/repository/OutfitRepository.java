package ar.edu.uai.lia.lookiate.repository;

import ar.edu.uai.lia.lookiate.model.Outfit;

import java.util.List;

public interface OutfitRepository extends Repository<Outfit, String> {
    List<Outfit> listFavorites();
    List<Outfit> findByUser(String userId);
    List<Outfit> findByOccasion(String occasion);
    List<Outfit> findByVisibility(Boolean visibility);
    void markFavorite(String id);
}
