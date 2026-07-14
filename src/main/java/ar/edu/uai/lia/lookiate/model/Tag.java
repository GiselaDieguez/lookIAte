package ar.edu.uai.lia.lookiate.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tags")
@Getter @Setter
@ToString
public class Tag {
    @Id
    private String tagId;
    private String garmentId;
    private String tagType;
    private String tagValue;

    @ManyToOne
    @JoinColumn(name = "garment_id")
    private Garment garment;
}
