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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OutfitServiceImplTest {

    @Mock
    private OutfitRepository outfitRepository;
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private GarmentRepository garmentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AIClient aiClient;

    @InjectMocks
    private OutfitServiceImpl outfitService;

    @Test
    void generateOutfitCreatesAndSavesOutfit() {
        Garment garment = new Garment();
        garment.setGarmentId("g1");
        when(aiClient.generateOutfit("ofi")).thenReturn(recommendation("g1"));
        when(garmentRepository.findById("g1")).thenReturn(Optional.of(garment));
        when(outfitRepository.save(any(Outfit.class))).thenAnswer(inv -> inv.getArgument(0));

        Outfit outfit = outfitService.generateOutfit("u1", "ofi");

        assertThat(outfit.getUserId()).isEqualTo("u1");
        assertThat(outfit.getLikesCount()).isZero();
        assertThat(outfit.getOutfitId()).isNotBlank();
        assertThat(outfit.getCreatedAt()).isNotNull();
        assertThat(outfit.getGarments()).containsExactly(garment);
    }

    @Test
    void generateOutfitThrowsWhenGarmentNotFound() {
        when(aiClient.generateOutfit("ofi")).thenReturn(recommendation("missing"));
        when(garmentRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> outfitService.generateOutfit("u1", "ofi"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("missing");
    }

    @Test
    void saveOutfitPersistsOutfit() {
        Outfit outfit = new Outfit();
        outfit.setOutfitId("o1");
        when(outfitRepository.findById("o1")).thenReturn(Optional.of(outfit));
        when(outfitRepository.save(any(Outfit.class))).thenAnswer(inv -> inv.getArgument(0));

        outfitService.saveOutfit("o1");

        verify(outfitRepository).save(outfit);
    }

    @Test
    void deleteOutfitRemovesIt() {
        Outfit outfit = new Outfit();
        outfit.setOutfitId("o1");
        when(outfitRepository.findById("o1")).thenReturn(Optional.of(outfit));

        outfitService.deleteOutfit("o1");

        verify(outfitRepository).delete("o1");
    }

    @Test
    void markAsFavoriteSetsFlagAndSaves() {
        Outfit outfit = new Outfit();
        outfit.setOutfitId("o1");
        when(outfitRepository.findById("o1")).thenReturn(Optional.of(outfit));
        when(outfitRepository.save(any(Outfit.class))).thenAnswer(inv -> inv.getArgument(0));

        outfitService.markAsFavorite("o1");

        assertThat(outfit.getIsFavorite()).isTrue();
        verify(outfitRepository).save(outfit);
    }

    @Test
    void getAlternativeGeneratesNewOutfitForSameUser() {
        Outfit current = new Outfit();
        current.setOutfitId("o1");
        current.setUserId("u1");
        current.setOccasion("ofi");
        current.setOutfitStyle("casual");
        current.setWeather("calido");
        when(outfitRepository.findById("o1")).thenReturn(Optional.of(current));
        when(aiClient.generateOutfit("ofi casual calido")).thenReturn(recommendation());
        when(outfitRepository.save(any(Outfit.class))).thenAnswer(inv -> inv.getArgument(0));

        Outfit alternative = outfitService.getAlternative("o1");

        assertThat(alternative.getUserId()).isEqualTo("u1");
        assertThat(alternative.getOutfitId()).isNotEqualTo("o1");
        verify(aiClient).generateOutfit("ofi casual calido");
    }

    @Test
    void getUserOutfitsDelegatesToRepository() {
        List<Outfit> outfits = List.of(new Outfit(), new Outfit());
        when(outfitRepository.findByUser("u1")).thenReturn(outfits);

        assertThat(outfitService.getUserOutfits("u1")).isEqualTo(outfits);
    }

    @Test
    void changeVisibilityUpdatesOutfit() {
        Outfit outfit = new Outfit();
        outfit.setOutfitId("o1");
        when(outfitRepository.findById("o1")).thenReturn(Optional.of(outfit));
        when(outfitRepository.save(any(Outfit.class))).thenAnswer(inv -> inv.getArgument(0));

        outfitService.changeVisibility("o1", true);

        assertThat(outfit.getVisibility()).isTrue();
        verify(outfitRepository).save(outfit);
    }

    @Test
    void likeOutfitCreatesLikeAndIncrementsLikes() {
        Outfit outfit = new Outfit();
        outfit.setOutfitId("o1");
        outfit.setLikesCount(0);
        User user = new User();
        user.setUserId("u1");
        when(outfitRepository.findById("o1")).thenReturn(Optional.of(outfit));
        when(likeRepository.findByUserIdAndOutfitId("u1", "o1")).thenReturn(null);
        when(userRepository.findById("u1")).thenReturn(Optional.of(user));
        when(likeRepository.save(any(Like.class))).thenAnswer(inv -> inv.getArgument(0));
        when(outfitRepository.save(any(Outfit.class))).thenAnswer(inv -> inv.getArgument(0));

        outfitService.likeOutfit("u1", "o1");

        assertThat(outfit.getLikesCount()).isEqualTo(1);
        verify(likeRepository).save(argThat(like ->
                "u1".equals(like.getUser().getUserId())
                        && "o1".equals(like.getOutfit().getOutfitId())
                        && like.getCreatedAt() != null
                        && like.getLikeId() != null));
        verify(outfitRepository).save(outfit);
    }

    @Test
    void likeOutfitDoesNothingWhenAlreadyLiked() {
        Outfit outfit = new Outfit();
        outfit.setOutfitId("o1");
        outfit.setLikesCount(1);
        when(outfitRepository.findById("o1")).thenReturn(Optional.of(outfit));
        when(likeRepository.findByUserIdAndOutfitId("u1", "o1")).thenReturn(new Like());

        outfitService.likeOutfit("u1", "o1");

        assertThat(outfit.getLikesCount()).isEqualTo(1);
        verify(likeRepository, never()).save(any(Like.class));
        verify(outfitRepository, never()).save(any(Outfit.class));
    }

    @Test
    void likeOutfitThrowsWhenUserNotFound() {
        Outfit outfit = new Outfit();
        outfit.setOutfitId("o1");
        when(outfitRepository.findById("o1")).thenReturn(Optional.of(outfit));
        when(likeRepository.findByUserIdAndOutfitId("missing", "o1")).thenReturn(null);
        when(userRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> outfitService.likeOutfit("missing", "o1"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("missing");
    }

    @Test
    void getPublicOutfitsReturnsVisibleOnes() {
        List<Outfit> publics = List.of(new Outfit());
        when(outfitRepository.findByVisibility(true)).thenReturn(publics);

        assertThat(outfitService.getPublicOutfits()).isEqualTo(publics);
    }

    private OutfitRecommendation recommendation(String... garmentIds) {
        OutfitRecommendation recommendation = new OutfitRecommendation();
        recommendation.setGarments(List.of(garmentIds));
        return recommendation;
    }
}
