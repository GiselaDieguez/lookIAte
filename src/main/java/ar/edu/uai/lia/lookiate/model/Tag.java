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
    private String tagType;
    private String tagValue;

    @ManyToOne
    @JoinColumn(name = "garment_id")
    @ToString.Exclude
    private Garment garment;

    public void create(String type, String value) {
        this.tagType = type;
        this.tagValue = value;
    }

    public void update(String newValue) {
        this.tagValue = newValue;
    }

    public void delete() {
    }
}
