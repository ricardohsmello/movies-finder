package br.com.ricas.infrastructure.db.mongodb.repo;

import br.com.ricas.domain.model.Movie;
import br.com.ricas.domain.port.MovieRepository;
import br.com.ricas.infrastructure.db.mongodb.document.MovieDocument;
import com.mongodb.client.AggregateIterable;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MovieRepositoryImpl implements PanacheMongoRepository<MovieDocument>, MovieRepository {

    @Override
    public List<Movie> findSimilar(List<Double> embedding) {
        List<Movie> movieList = new ArrayList<>();

        try {
            AggregateIterable<MovieDocument> aggregate = mongoCollection().aggregate(List.of(
                    new Document(
                            "$vectorSearch",
                            new Document("queryVector", embedding)
                                    .append("path", "movie_embedded")
                                    .append("numCandidates", 20)
                                    .append("index", "vector_index")
                                    .append("limit", 15)
                    ),
                    new Document("$sort", new Document("imdb.rating", -1))
            ));

            for (MovieDocument movieEntity : aggregate) {
                movieList.add(movieEntity.toDomain());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return movieList;
    }
}