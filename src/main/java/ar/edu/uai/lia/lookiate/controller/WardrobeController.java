package ar.edu.uai.lia.lookiate.controller;

import ar.edu.uai.lia.lookiate.model.Garment;
import ar.edu.uai.lia.lookiate.model.Wardrobe;
import ar.edu.uai.lia.lookiate.service.WardrobeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/wardrobe")
public class WardrobeController {

    private final WardrobeService wardrobeService;

    public WardrobeController(WardrobeService wardrobeService) {
        this.wardrobeService = wardrobeService;
    }

    @PostMapping
    public ResponseEntity<Wardrobe> create(@RequestBody Wardrobe wardrobe) {
        return ResponseEntity.ok(wardrobeService.createWardrobe(wardrobe));
    }

    @GetMapping("/{wardrobeId}")
    public ResponseEntity<Wardrobe> getDetails(@PathVariable String wardrobeId) {
        return ResponseEntity.ok(wardrobeService.getDetails(wardrobeId));
    }

    @GetMapping("/{wardrobeId}/garments")
    public ResponseEntity<List<Garment>> listGarments(@PathVariable String wardrobeId) {
        return ResponseEntity.ok(wardrobeService.listGarments(wardrobeId));
    }

    @PostMapping("/{wardrobeId}/garments/{garmentId}")
    public ResponseEntity<Void> addGarment(@PathVariable String wardrobeId, @PathVariable String garmentId) {
        wardrobeService.addGarment(wardrobeId, garmentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{wardrobeId}/garments/{garmentId}")
    public ResponseEntity<Void> removeGarment(@PathVariable String wardrobeId, @PathVariable String garmentId) {
        wardrobeService.removeGarment(wardrobeId, garmentId);
        return ResponseEntity.noContent().build();
    }
}
