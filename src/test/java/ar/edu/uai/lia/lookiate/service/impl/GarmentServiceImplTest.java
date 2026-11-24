package ar.edu.uai.lia.lookiate.service.impl;

import ar.edu.uai.lia.lookiate.client.AIClient;
import ar.edu.uai.lia.lookiate.model.Garment;
import ar.edu.uai.lia.lookiate.model.Tag;
import ar.edu.uai.lia.lookiate.repository.GarmentRepository;
import ar.edu.uai.lia.lookiate.repository.OutfitRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GarmentServiceImplTest {

    @Mock
    private GarmentRepository garmentRepository;

    @Mock
    private OutfitRepository outfitRepository;

    @Mock
    private AIClient aiClient;

    @InjectMocks
    private GarmentServiceImpl garmentService;

    @Test
    void createGarmentSetsDateAndTagsFromAi() {
        Garment garment = new Garment();
        garment.setGarmentId("g1");
        garment.setGarmentImg("img-base64");
        List<Tag> tags = List.of(new Tag());
        when(aiClient.generateTags("img-base64")).thenReturn(tags);
        when(garmentRepository.save(any(Garment.class))).thenAnswer(inv -> inv.getArgument(0));

        Garment created = garmentService.createGarment(garment);

        assertThat(created.getCreatedAt()).isNotNull();
        assertThat(created.getTags()).isEqualTo(tags);
        verify(garmentRepository).save(garment);
    }

    @Test
    void updateImageChangesImageAndRegeneratesTags() {
        Garment garment = new Garment();
        garment.setGarmentId("g1");
        garment.setGarmentImg("vieja");
        List<Tag> tags = List.of(new Tag());
        when(garmentRepository.findById("g1")).thenReturn(Optional.of(garment));
        when(aiClient.generateTags("nueva")).thenReturn(tags);
        when(garmentRepository.save(any(Garment.class))).thenAnswer(inv -> inv.getArgument(0));

        garmentService.updateImage("g1", "nueva");

        assertThat(garment.getGarmentImg()).isEqualTo("nueva");
        assertThat(garment.getTags()).isEqualTo(tags);
        verify(garmentRepository).save(garment);
    }

    @Test
    void updateTagsReplacesTags() {
        Garment garment = new Garment();
        garment.setGarmentId("g1");
        List<Tag> newTags = List.of(new Tag());
        when(garmentRepository.findById("g1")).thenReturn(Optional.of(garment));
        when(garmentRepository.save(any(Garment.class))).thenAnswer(inv -> inv.getArgument(0));

        garmentService.updateTags("g1", newTags);

        assertThat(garment.getTags()).isEqualTo(newTags);
        verify(garmentRepository).save(garment);
    }

    @Test
    void getGarmentDetailsReturnsGarment() {
        Garment garment = new Garment();
        garment.setGarmentId("g1");
        when(garmentRepository.findById("g1")).thenReturn(Optional.of(garment));

        assertThat(garmentService.getGarmentDetails("g1")).isEqualTo(garment);
    }

    @Test
    void getGarmentDetailsThrowsWhenNotFound() {
        when(garmentRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> garmentService.getGarmentDetails("missing"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("missing");
    }

    @Test
    void deleteRemovesGarment() {
        Garment garment = new Garment();
        garment.setGarmentId("g1");
        when(garmentRepository.findById("g1")).thenReturn(Optional.of(garment));

        garmentService.delete("g1");

        verify(garmentRepository).delete("g1");
    }

    @Test
    void getUserGarmentsReturnsEmptyListWhileUnimplemented() {
        assertThat(garmentService.getUserGarments("u1")).isEmpty();
    }
}
