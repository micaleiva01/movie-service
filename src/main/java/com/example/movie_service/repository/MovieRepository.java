package com.example.movie_service.repository;


import com.example.movie_service.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContaining(String title);
}
