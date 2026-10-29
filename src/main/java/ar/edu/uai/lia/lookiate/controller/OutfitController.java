package ar.edu.uai.lia.lookiate.controller;

import ar.edu.uai.lia.lookiate.model.Outfit;
import ar.edu.uai.lia.lookiate.service.OutfitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/outfit")
public class OutfitController {

    private final OutfitService outfitService;

    public OutfitController(OutfitService outfitService) {
        this.outfitService = outfitService;
    }

    @PostMapping("/generate")
    public ResponseEntity<Outfit> generate(@RequestParam String userId, @RequestParam String context) {
        return ResponseEntity.ok(outfitService.generateOutfit(userId, context));
    }

    @PostMapping("/{outfitId}/save")
    public ResponseEntity<Void> save(@PathVariable String outfitId) {
        outfitService.saveOutfit(outfitId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{outfitId}")
    public ResponseEntity<Void> delete(@PathVariable String outfitId) {
        outfitService.deleteOutfit(outfitId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{outfitId}/favorite")
    public ResponseEntity<Void> markFavorite(@PathVariable String outfitId) {
        outfitService.markAsFavorite(outfitId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{outfitId}/alternative")
    public ResponseEntity<Outfit> getAlternative(@PathVariable String outfitId) {
        return ResponseEntity.ok(outfitService.getAlternative(outfitId));
    }

    @PatchMapping("/{outfitId}/visibility")
    public ResponseEntity<Void> setVisibility(@PathVariable String outfitId, @RequestParam Boolean visibility) {
        outfitService.changeVisibility(outfitId, visibility);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{outfitId}/like")
    public ResponseEntity<Void> toggleLike(@RequestParam String userId, @PathVariable String outfitId) {
        outfitService.likeOutfit(userId, outfitId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public")
    public ResponseEntity<List<Outfit>> getPublicOutfits() {
        return ResponseEntity.ok(outfitService.getPublicOutfits());
    }
}
