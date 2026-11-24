package ar.edu.uai.lia.lookiate.controller;

import ar.edu.uai.lia.lookiate.model.Garment;
import ar.edu.uai.lia.lookiate.model.Wardrobe;
import ar.edu.uai.lia.lookiate.service.WardrobeService;
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
class WardrobeControllerTest {

    @Mock
    private WardrobeService wardrobeService;

    @InjectMocks
    private WardrobeController wardrobeController;

    @Test
    void createReturnsSavedWardrobe() {
        Wardrobe wardrobe = new Wardrobe();
        wardrobe.setWardrobeId("w1");
        when(wardrobeService.createWardrobe(wardrobe)).thenReturn(wardrobe);

        ResponseEntity<Wardrobe> response = wardrobeController.create(wardrobe);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(wardrobe);
    }

    @Test
    void getDetailsReturnsWardrobe() {
        Wardrobe wardrobe = new Wardrobe();
        wardrobe.setWardrobeId("w1");
        when(wardrobeService.getDetails("w1")).thenReturn(wardrobe);

        ResponseEntity<Wardrobe> response = wardrobeController.getDetails("w1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(wardrobe);
    }

    @Test
    void listGarmentsReturnsGarments() {
        List<Garment> garments = List.of(new Garment());
        when(wardrobeService.listGarments("w1")).thenReturn(garments);

        ResponseEntity<List<Garment>> response = wardrobeController.listGarments("w1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(garments);
    }

    @Test
    void addGarmentReturnsNoContent() {
        ResponseEntity<Void> response = wardrobeController.addGarment("w1", "g1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(wardrobeService).addGarment("w1", "g1");
    }

    @Test
    void removeGarmentReturnsNoContent() {
        ResponseEntity<Void> response = wardrobeController.removeGarment("w1", "g1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(wardrobeService).removeGarment("w1", "g1");
    }
}
