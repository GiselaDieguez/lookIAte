package ar.edu.uai.lia.lookiate.service;

import ar.edu.uai.lia.lookiate.model.Outfit;

import java.util.List;

public interface OutfitService {
    Outfit generateOutfit(String userId, String context);
    void saveOutfit(String outfitId);
    void deleteOutfit(String outfitId);
    void markAsFavorite(String outfitId);
    Outfit getAlternative(String outfitId);
    List<Outfit> getUserOutfits(String userId);
    void changeVisibility(String outfitId, Boolean visibility);
    void likeOutfit(String userId, String outfitId);
    List<Outfit> getPublicOutfits();
}
