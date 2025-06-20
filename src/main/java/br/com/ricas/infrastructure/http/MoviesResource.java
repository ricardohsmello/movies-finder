package br.com.ricas.infrastructure.http;

import br.com.ricas.application.service.MovieService;
import br.com.ricas.infrastructure.http.dto.MovieRequest;
import br.com.ricas.infrastructure.http.dto.MovieResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/movies")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class MoviesResource {

    @Inject
    MovieService movieService;

    @Path("/findSimilar")
    @POST
    public List<MovieResponse> findSimilar(MovieRequest request) {
        return movieService.findSimilar(
                request.input()).stream().map(MovieResponse::fromDomain).toList();
    }
}


