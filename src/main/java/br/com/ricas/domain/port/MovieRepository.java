package br.com.ricas.domain.port;

import br.com.ricas.domain.model.Movie;

import java.util.List;

public interface MovieRepository {
    List<Movie> findSimilar(List<Double> embedding);
}
