package ar.edu.uai.lia.lookiate.client;

import ar.edu.uai.lia.lookiate.model.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class RestAIClient extends AIClient {

    private final RestClient restClient;

    public RestAIClient(RestClient.Builder builder,
                        @Value("${ai.client.base-url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    @Override
    public OutfitRecommendation generateOutfit(Object request) {
        return restClient.post()
                .uri("/ai/outfit")
                .body(request)
                .retrieve()
                .body(OutfitRecommendation.class);
    }

    @Override
    public List<Tag> generateTags(String image) {
        return restClient.post()
                .uri("/ai/tags")
                .body(image)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Tag>>() {
                });
    }
}
