package ar.edu.uai.lia.lookiate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "garments")
@Getter @Setter
@ToString
public class Garment {
    @Id
    private String garmentId;
    private String garmentImg;
    private Date createdAt;
    private Float confidenceLevel;

    @ManyToOne
    @JoinColumn(name = "wardrobe_id")
    private Wardrobe wardrobe;

    @OneToMany(mappedBy = "garment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags;

    public void updateTags(List<Tag> newTags) {
        this.tags = newTags;
    }

    public void updateImage(String newImage) {
        this.garmentImg = newImage;
    }

    public void delete() {
    }
}
