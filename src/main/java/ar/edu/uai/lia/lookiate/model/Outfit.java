package ar.edu.uai.lia.lookiate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "outfits")
@Getter @Setter
@ToString
public class Outfit {
    @Id
    private String outfitId;
    private String occasion;
    private String outfitStyle;
    private String weather;
    private Date createdAt;
    private Boolean isFavorite;
    private Integer likesCount = 0;
    private Boolean visibility;
    private String userId;

    @ManyToMany
    @JoinTable(
            name = "outfit_garments",
            joinColumns = @JoinColumn(name = "outfit_id"),
            inverseJoinColumns = @JoinColumn(name = "garment_id"))
    private List<Garment> garments;

    public void saveOutfit() {
    }

    public void deleteOutfit() {
    }

    public void markAsFavorite() {
        this.isFavorite = true;
    }

    public void getAlternative() {
    }

    public void markAsPrivate() {
        this.visibility = false;
    }

    public void incrementLikes() {
        this.likesCount++;
    }

    public void decrementLikes() {
        this.likesCount--;
    }

}
