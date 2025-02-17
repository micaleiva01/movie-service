package com.example.movie_service.service;

import com.example.movie_service.model.Movie;
import com.example.movie_service.model.Actor;
import com.example.movie_service.repository.MovieRepository;
import com.example.movie_service.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements IMovieService{

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pelicula con id: " + id + " no encontrada."));
    }

    @Override
    public Movie updateMovie(Long id, Movie movieDetails) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);

        if (optionalMovie.isPresent()) {
            Movie existingMovie = optionalMovie.get();

            System.out.println("Received image in update request: " + movieDetails.getImage());

            System.out.println("Movie ID: " + id);
            System.out.println("New Image URL: " + movieDetails.getImage());

            existingMovie.setTitle(movieDetails.getTitle());
            existingMovie.setYear(movieDetails.getYear());
            existingMovie.setDuration(movieDetails.getDuration());
            existingMovie.setCountry(movieDetails.getCountry());
            existingMovie.setDirector(movieDetails.getDirector());
            existingMovie.setGenre(movieDetails.getGenre());
            existingMovie.setSynopsis(movieDetails.getSynopsis());
            existingMovie.setImage(movieDetails.getImage());

            return movieRepository.save(existingMovie);
        } else {
            throw new RuntimeException("Movie not found with id: " + id);
        }
    }


    @Override
    public void deleteMovie(Long id) {
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
        } else {
            throw new RuntimeException("No se ha podido eliminar pelicula. Pelicula con id: " + id + " no encontrada.");
        }
    }

    @Override
    public Movie addActorToMovie(Long movieId, Long actorId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Pelicula con el siguiente ID no encontrada: " + movieId));

        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new RuntimeException("Actor con el siguiente ID no encontrado: " + actorId));

        movie.getActors().add(actor);
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> searchMovies(String query) {
        return movieRepository.findAll().stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(query.toLowerCase())
                        || movie.getGenre().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}