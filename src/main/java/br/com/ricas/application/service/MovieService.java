package br.com.ricas.application.service;

import br.com.ricas.domain.model.Movie;
import br.com.ricas.domain.port.MovieRepository;
import br.com.ricas.domain.port.OpenAIGateway;

import java.util.List;

public class MovieService {

    private final MovieRepository movieRepository;
    private final OpenAIGateway openAIGateway;

    public MovieService(
            MovieRepository movieRepository ,
            OpenAIGateway openAIGateway) {
        this.movieRepository = movieRepository;
        this.openAIGateway = openAIGateway;
    }

    public List<Movie> findSimilar(String input) {
        List<Double> embedding = getEmbedding(input);
        return movieRepository.findSimilar(embedding);
    }

    private List<Double> getEmbedding(String input) {
        return openAIGateway.getEmbedding(input);
    }
}
