package br.com.ricas.infrastructure.config;

import br.com.ricas.application.service.MovieService;
import br.com.ricas.domain.port.MovieRepository;
import br.com.ricas.domain.port.OpenAIGateway;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class ServiceConfig {

    @Produces
    MovieService produceMovieService(
            MovieRepository movieRepository,
            OpenAIGateway openAIGateway
    ) {
        return new MovieService(movieRepository, openAIGateway);
    }

}
