package ar.edu.uai.lia.lookiate.controller;

import ar.edu.uai.lia.lookiate.model.Garment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;

import ar.edu.uai.lia.lookiate.service.GarmentService;


@RestController
@RequestMapping(value= "/garment")
public class GarmentController {

    private GarmentService garmentService;

    public GarmentController(GarmentService garmentService) {
        this.garmentService = garmentService;
    }

    @PostMapping
    public ResponseEntity<Garment> create(@RequestBody Garment garment) {
        return ResponseEntity.ok(garmentService.createGarment(garment));
    }

    @PatchMapping("/update-img")
    public ResponseEntity<Void> updateImage(@RequestBody Garment garment){
        garmentService.updateImage(garment.getGarmentId(), garment.getGarmentImg());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update-tags")
    public ResponseEntity<Void> updateTags(@RequestBody Garment garment){
        garmentService.updateTags(garment.getGarmentId(), garment.getTags());
        return ResponseEntity.noContent().build();
    }
}