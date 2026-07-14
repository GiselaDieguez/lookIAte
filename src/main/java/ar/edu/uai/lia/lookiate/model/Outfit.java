package ar.edu.uai.lia.lookiate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Entity
@Table(name = "outfits")
@Getter @Setter
@ToString
public class Outfit {
    @Id
    private String outfitId;
    private String ocassion;
    private String outfitStyle;
    private String weather;
    private Date createdAt;
    private Boolean isFavorite;
    private Garment garment;

}
