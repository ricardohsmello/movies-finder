package br.com.ricas.infrastructure.gateway;

import br.com.ricas.domain.port.OpenAIGateway;
import br.com.ricas.infrastructure.config.OpenAIConfig;
import br.com.ricas.infrastructure.gateway.dto.OpenAIRequest;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class OpenAIGatewayImpl implements OpenAIGateway {

    @Inject
    OpenAIConfig openAIConfig;

    @Override
    public List<Double> getEmbedding(String input) {
        var openAiGateway = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(openAIConfig.getOpenaiUrl()))
                .build(OpenAIRestClient.class);

        var embedding = openAiGateway.embedding(
                new OpenAIRequest(input, openAIConfig.getModel()),
                openAIConfig.getToken()
        );

        return embedding.data().getFirst().embedding();
    }
}