package ar.edu.uai.lia.lookiate.controller;

import ar.edu.uai.lia.lookiate.model.Outfit;
import ar.edu.uai.lia.lookiate.service.OutfitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OutfitControllerTest {

    @Mock
    private OutfitService outfitService;

    @InjectMocks
    private OutfitController outfitController;

    @Test
    void generateReturnsGeneratedOutfit() {
        Outfit outfit = new Outfit();
        outfit.setOutfitId("o1");
        when(outfitService.generateOutfit("u1", "ofi")).thenReturn(outfit);

        ResponseEntity<Outfit> response = outfitController.generate("u1", "ofi");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(outfit);
    }

    @Test
    void saveReturnsNoContent() {
        ResponseEntity<Void> response = outfitController.save("o1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(outfitService).saveOutfit("o1");
    }

    @Test
    void deleteReturnsNoContent() {
        ResponseEntity<Void> response = outfitController.delete("o1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(outfitService).deleteOutfit("o1");
    }

    @Test
    void markFavoriteReturnsNoContent() {
        ResponseEntity<Void> response = outfitController.markFavorite("o1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(outfitService).markAsFavorite("o1");
    }

    @Test
    void getAlternativeReturnsOutfit() {
        Outfit alternative = new Outfit();
        alternative.setOutfitId("o2");
        when(outfitService.getAlternative("o1")).thenReturn(alternative);

        ResponseEntity<Outfit> response = outfitController.getAlternative("o1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(alternative);
    }

    @Test
    void setVisibilityReturnsNoContent() {
        ResponseEntity<Void> response = outfitController.setVisibility("o1", true);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(outfitService).changeVisibility("o1", true);
    }

    @Test
    void toggleLikeReturnsNoContent() {
        ResponseEntity<Void> response = outfitController.toggleLike("u1", "o1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(outfitService).likeOutfit("u1", "o1");
    }

    @Test
    void getPublicOutfitsReturnsList() {
        List<Outfit> publics = List.of(new Outfit());
        when(outfitService.getPublicOutfits()).thenReturn(publics);

        ResponseEntity<List<Outfit>> response = outfitController.getPublicOutfits();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(publics);
    }
}
