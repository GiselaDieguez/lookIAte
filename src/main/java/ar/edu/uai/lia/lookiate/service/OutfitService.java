package ar.edu.uai.lia.lookiate.service;

import ar.edu.uai.lia.lookiate.model.Outfit;

import java.util.List;

public interface OutfitService {
    Outfit createOutfit(Outfit outfit);
    Outfit generateOutfit(Long userId);
    List<Outfit> getFavoriteOutfits(Long userId);
    void markAsFavorite(Long outfitId);
    void delete(Long outfitId);
}
