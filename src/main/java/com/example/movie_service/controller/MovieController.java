package com.example.movie_service.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.movie_service.model.Movie;
import com.example.movie_service.service.IMovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);
    private final IMovieService movieService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MovieController(IMovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>>getMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createMovie(@RequestBody String movieJson, @RequestHeader HttpHeaders headers) {
        logger.info("headers: {}", headers);
        logger.info("payload: {}", movieJson);

        try {
            Movie movie = objectMapper.readValue(movieJson, Movie.class);
            logger.info("Objecto creado: {}", movie);

            if (movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Se requiere titulo.");
            }

            Movie savedMovie = movieService.saveMovie(movie);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedMovie);

        } catch (Exception e) {
            logger.error("Error", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato invalido.");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        if (movie != null) {
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam String query) {
        List<Movie> filteredMovies = movieService.searchMovies(query);
        return ResponseEntity.ok(filteredMovies);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movieDetails) {
        try {
            System.out.println("Updating movie ID: " + id);
            System.out.println("New Image URL: " + movieDetails.getImage());

            Movie updatedMovie = movieService.updateMovie(id, movieDetails);

            return ResponseEntity.ok(updatedMovie);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Pelicula eliminada con exito");
    }

    @PostMapping("/{movieId}/actors/{actorId}")
    public ResponseEntity<Movie> addActorToMovie(@PathVariable Long movieId, @PathVariable Long actorId) {
        try {
            Movie updatedMovie = movieService.addActorToMovie(movieId, actorId);
            return ResponseEntity.ok(updatedMovie);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

