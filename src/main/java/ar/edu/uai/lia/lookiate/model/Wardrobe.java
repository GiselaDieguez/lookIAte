package ar.edu.uai.lia.lookiate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "wardrobes")
@Getter @Setter
@ToString
public class Wardrobe {

    @Id
    private String wardrobeId;
    private String name;
    @ManyToOne
    @JoinColumn(name = "garment_id")
    private Garment garment;

}
