package ar.edu.uai.lia.lookiate.service.impl;

import ar.edu.uai.lia.lookiate.client.AIClient;
import ar.edu.uai.lia.lookiate.client.OutfitRecommendation;
import ar.edu.uai.lia.lookiate.model.Garment;
import ar.edu.uai.lia.lookiate.model.Like;
import ar.edu.uai.lia.lookiate.model.Outfit;
import ar.edu.uai.lia.lookiate.model.User;
import ar.edu.uai.lia.lookiate.repository.GarmentRepository;
import ar.edu.uai.lia.lookiate.repository.LikeRepository;
import ar.edu.uai.lia.lookiate.repository.OutfitRepository;
import ar.edu.uai.lia.lookiate.repository.UserRepository;
import ar.edu.uai.lia.lookiate.service.OutfitService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OutfitServiceImpl implements OutfitService {

    private final OutfitRepository outfitRepository;
    private final LikeRepository likeRepository;
    private final GarmentRepository garmentRepository;
    private final UserRepository userRepository;
    private final AIClient aiClient;

    public OutfitServiceImpl(OutfitRepository outfitRepository,
                             LikeRepository likeRepository,
                             GarmentRepository garmentRepository,
                             UserRepository userRepository,
                             AIClient aiClient) {
        this.outfitRepository = outfitRepository;
        this.likeRepository = likeRepository;
        this.garmentRepository = garmentRepository;
        this.userRepository = userRepository;
        this.aiClient = aiClient;
    }

    @Override
    public Outfit generateOutfit(String userId, String context) {
        OutfitRecommendation recommendation = aiClient.generateOutfit(context);
        return createAndSaveOutfit(userId, recommendation);
    }

    @Override
    public void saveOutfit(String outfitId) {
        Outfit outfit = getOutfitOrThrow(outfitId);
        outfit.saveOutfit();
        outfitRepository.save(outfit);
    }

    @Override
    public void deleteOutfit(String outfitId) {
        getOutfitOrThrow(outfitId);
        outfitRepository.delete(outfitId);
    }

    @Override
    public void markAsFavorite(String outfitId) {
        Outfit outfit = getOutfitOrThrow(outfitId);
        outfit.markAsFavorite();
        outfitRepository.save(outfit);
    }

    @Override
    public Outfit getAlternative(String outfitId) {
        Outfit current = getOutfitOrThrow(outfitId);
        String context = current.getOccasion() + " " + current.getOutfitStyle() + " " + current.getWeather();
        OutfitRecommendation recommendation = aiClient.generateOutfit(context);
        return createAndSaveOutfit(current.getUserId(), recommendation);
    }

    @Override
    public List<Outfit> getUserOutfits(String userId) {
        return outfitRepository.findByUser(userId);
    }

    @Override
    public void changeVisibility(String outfitId, Boolean visibility) {
        Outfit outfit = getOutfitOrThrow(outfitId);
        outfit.setVisibility(visibility);
        outfitRepository.save(outfit);
    }

    @Override
    public void likeOutfit(String userId, String outfitId) {
        Outfit outfit = getOutfitOrThrow(outfitId);
        if (likeRepository.findByUserIdAndOutfitId(userId, outfitId) != null) {
            return;
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        Like like = new Like();
        like.setLikeId(UUID.randomUUID().toString());
        like.setCreatedAt(new Date());
        like.setUser(user);
        like.setOutfit(outfit);
        likeRepository.save(like);
        outfit.incrementLikes();
        outfitRepository.save(outfit);
    }

    @Override
    public List<Outfit> getPublicOutfits() {
        return outfitRepository.findByVisibility(true);
    }

    private Outfit createAndSaveOutfit(String userId, OutfitRecommendation recommendation) {
        Outfit outfit = new Outfit();
        outfit.setOutfitId(UUID.randomUUID().toString());
        outfit.setUserId(userId);
        outfit.setCreatedAt(new Date());
        outfit.setLikesCount(0);
        outfit.setGarments(resolveGarments(recommendation.getGarments()));
        return outfitRepository.save(outfit);
    }

    private List<Garment> resolveGarments(List<String> garmentIds) {
        if (garmentIds == null) {
            return List.of();
        }
        return garmentIds.stream()
                .map(garmentId -> garmentRepository.findById(garmentId)
                        .orElseThrow(() -> new RuntimeException("Garment not found: " + garmentId)))
                .toList();
    }

    private Outfit getOutfitOrThrow(String outfitId) {
        return outfitRepository.findById(outfitId)
                .orElseThrow(() -> new RuntimeException("Outfit not found: " + outfitId));
    }
}
