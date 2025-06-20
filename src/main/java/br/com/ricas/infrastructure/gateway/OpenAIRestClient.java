package br.com.ricas.infrastructure.gateway;

import br.com.ricas.infrastructure.gateway.dto.OpenAIRequest;
import br.com.ricas.infrastructure.gateway.dto.OpenAIResponse;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
public interface OpenAIRestClient {

    @POST
    OpenAIResponse embedding(OpenAIRequest request, @HeaderParam("Authorization") String authorization);
}