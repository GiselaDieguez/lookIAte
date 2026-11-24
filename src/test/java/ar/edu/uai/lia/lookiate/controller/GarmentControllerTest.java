package ar.edu.uai.lia.lookiate.controller;

import ar.edu.uai.lia.lookiate.model.Garment;
import ar.edu.uai.lia.lookiate.model.Tag;
import ar.edu.uai.lia.lookiate.service.GarmentService;
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
class GarmentControllerTest {

    @Mock
    private GarmentService garmentService;

    @InjectMocks
    private GarmentController garmentController;

    @Test
    void createReturnsSavedGarment() {
        Garment garment = new Garment();
        garment.setGarmentId("g1");
        when(garmentService.createGarment(garment)).thenReturn(garment);

        ResponseEntity<Garment> response = garmentController.create(garment);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(garment);
    }

    @Test
    void updateImageReturnsNoContent() {
        ResponseEntity<Void> response = garmentController.updateImage("g1", "nueva-img");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(garmentService).updateImage("g1", "nueva-img");
    }

    @Test
    void updateTagsReturnsNoContent() {
        List<Tag> newTags = List.of(new Tag());

        ResponseEntity<Void> response = garmentController.updateTags("g1", newTags);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(garmentService).updateTags("g1", newTags);
    }

    @Test
    void getDetailsReturnsGarment() {
        Garment garment = new Garment();
        garment.setGarmentId("g1");
        when(garmentService.getGarmentDetails("g1")).thenReturn(garment);

        ResponseEntity<Garment> response = garmentController.getDetails("g1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(garment);
    }

    @Test
    void deleteReturnsNoContent() {
        ResponseEntity<Void> response = garmentController.delete("g1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(garmentService).delete("g1");
    }
}
