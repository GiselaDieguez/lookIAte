package ar.edu.uai.lia.lookiate.client;

import ar.edu.uai.lia.lookiate.model.Tag;

import java.util.List;

public abstract class AIClient {
    public abstract OutfitRecommendation generateOutfit(Object request);
    public abstract List<Tag> generateTags(String image);
}
