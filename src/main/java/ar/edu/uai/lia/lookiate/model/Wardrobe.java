package ar.edu.uai.lia.lookiate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wardrobes")
@Getter @Setter
@ToString
public class Wardrobe {

    @Id
    private String wardrobeId;
    private String name;

    @OneToMany(mappedBy = "wardrobe")
    @ToString.Exclude
    private List<Garment> garments = new ArrayList<>();

    public void getWardrobe() {
    }

    public void addGarment(Garment garment) {
        this.garments.add(garment);
    }

    public void removeGarment(String garmentId) {
        this.garments.removeIf(garment -> garment.getGarmentId().equals(garmentId));
    }

    public void getClothingList() {
    }

    public void searchClothing(String filters) {
    }

}
