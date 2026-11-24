package ar.edu.uai.lia.lookiate.service.impl;

import ar.edu.uai.lia.lookiate.model.Garment;
import ar.edu.uai.lia.lookiate.model.Wardrobe;
import ar.edu.uai.lia.lookiate.repository.GarmentRepository;
import ar.edu.uai.lia.lookiate.repository.WardrobeRepository;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WardrobeServiceImplTest {

    @Mock
    private WardrobeRepository wardrobeRepository;

    @Mock
    private GarmentRepository garmentRepository;

    @InjectMocks
    private WardrobeServiceImpl wardrobeService;

    @Test
    void createWardrobeSavesAndReturns() {
        Wardrobe wardrobe = new Wardrobe();
        wardrobe.setWardrobeId("w1");
        when(wardrobeRepository.save(any(Wardrobe.class))).thenAnswer(inv -> inv.getArgument(0));

        Wardrobe created = wardrobeService.createWardrobe(wardrobe);

        assertThat(created).isEqualTo(wardrobe);
        verify(wardrobeRepository).save(wardrobe);
    }

    @Test
    void getDetailsReturnsWardrobe() {
        Wardrobe wardrobe = new Wardrobe();
        wardrobe.setWardrobeId("w1");
        when(wardrobeRepository.findById("w1")).thenReturn(Optional.of(wardrobe));

        assertThat(wardrobeService.getDetails("w1")).isEqualTo(wardrobe);
    }

    @Test
    void getDetailsThrowsWhenNotFound() {
        when(wardrobeRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> wardrobeService.getDetails("missing"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("missing");
    }

    @Test
    void listGarmentsDelegatesToGarmentRepository() {
        List<Garment> garments = List.of(new Garment());
        when(garmentRepository.findByWardrobe("w1")).thenReturn(garments);

        assertThat(wardrobeService.listGarments("w1")).isEqualTo(garments);
    }

    @Test
    void addGarmentAssociatesAndSavesBothSides() {
        Wardrobe wardrobe = new Wardrobe();
        wardrobe.setWardrobeId("w1");
        Garment garment = new Garment();
        garment.setGarmentId("g1");
        when(wardrobeRepository.findById("w1")).thenReturn(Optional.of(wardrobe));
        when(garmentRepository.findById("g1")).thenReturn(Optional.of(garment));
        when(wardrobeRepository.save(any(Wardrobe.class))).thenAnswer(inv -> inv.getArgument(0));

        wardrobeService.addGarment("w1", "g1");

        assertThat(wardrobe.getGarments()).contains(garment);
        assertThat(garment.getWardrobe()).isEqualTo(wardrobe);
        verify(wardrobeRepository).save(wardrobe);
        verify(garmentRepository).save(garment);
    }

    @Test
    void addGarmentThrowsWhenWardrobeNotFound() {
        when(wardrobeRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> wardrobeService.addGarment("missing", "g1"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("missing");
        verify(garmentRepository, never()).findById("g1");
    }

    @Test
    void addGarmentThrowsWhenGarmentNotFound() {
        Wardrobe wardrobe = new Wardrobe();
        wardrobe.setWardrobeId("w1");
        when(wardrobeRepository.findById("w1")).thenReturn(Optional.of(wardrobe));
        when(garmentRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> wardrobeService.addGarment("w1", "missing"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("missing");
        verify(wardrobeRepository, never()).save(any(Wardrobe.class));
    }

    @Test
    void removeGarmentDisassociatesAndSavesBothSides() {
        Wardrobe wardrobe = new Wardrobe();
        wardrobe.setWardrobeId("w1");
        Garment garment = new Garment();
        garment.setGarmentId("g1");
        wardrobe.addGarment(garment);
        garment.setWardrobe(wardrobe);
        when(wardrobeRepository.findById("w1")).thenReturn(Optional.of(wardrobe));
        when(garmentRepository.findById("g1")).thenReturn(Optional.of(garment));
        when(wardrobeRepository.save(any(Wardrobe.class))).thenAnswer(inv -> inv.getArgument(0));

        wardrobeService.removeGarment("w1", "g1");

        assertThat(wardrobe.getGarments()).doesNotContain(garment);
        assertThat(garment.getWardrobe()).isNull();
        verify(wardrobeRepository).save(wardrobe);
        verify(garmentRepository).save(garment);
    }

    @Test
    void removeGarmentThrowsWhenWardrobeNotFound() {
        when(wardrobeRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> wardrobeService.removeGarment("missing", "g1"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("missing");
    }

    @Test
    void removeGarmentThrowsWhenGarmentNotFound() {
        Wardrobe wardrobe = new Wardrobe();
        wardrobe.setWardrobeId("w1");
        when(wardrobeRepository.findById("w1")).thenReturn(Optional.of(wardrobe));
        when(garmentRepository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> wardrobeService.removeGarment("w1", "missing"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("missing");
        verify(wardrobeRepository, never()).save(any(Wardrobe.class));
    }
}
