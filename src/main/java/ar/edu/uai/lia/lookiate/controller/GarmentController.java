package ar.edu.uai.lia.lookiate.controller;

import ar.edu.uai.lia.lookiate.model.Garment;
import ar.edu.uai.lia.lookiate.model.Tag;
import ar.edu.uai.lia.lookiate.service.GarmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/garment")
public class GarmentController {

    private final GarmentService garmentService;

    public GarmentController(GarmentService garmentService) {
        this.garmentService = garmentService;
    }

    @PostMapping
    public ResponseEntity<Garment> create(@RequestBody Garment garment) {
        return ResponseEntity.ok(garmentService.createGarment(garment));
    }

    @PatchMapping("/{garmentId}/update-img")
    public ResponseEntity<Void> updateImage(@PathVariable String garmentId, @RequestBody String image) {
        garmentService.updateImage(garmentId, image);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{garmentId}/update-tags")
    public ResponseEntity<Void> updateTags(@PathVariable String garmentId, @RequestBody List<Tag> newTags) {
        garmentService.updateTags(garmentId, newTags);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{garmentId}")
    public ResponseEntity<Garment> getDetails(@PathVariable String garmentId) {
        return ResponseEntity.ok(garmentService.getGarmentDetails(garmentId));
    }

    @DeleteMapping("/{garmentId}")
    public ResponseEntity<Void> delete(@PathVariable String garmentId) {
        garmentService.delete(garmentId);
        return ResponseEntity.noContent().build();
    }
}
