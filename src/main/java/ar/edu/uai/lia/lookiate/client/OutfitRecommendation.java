package ar.edu.uai.lia.lookiate.client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter
@ToString
public class OutfitRecommendation {
    private List<String> garments;
    private String explanation;
    private Float confidence;
}
