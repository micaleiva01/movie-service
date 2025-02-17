package com.example.movie_service.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int year;
    private int duration;
    private String country;
    private String director;
    private String genre;
    private String synopsis;
    private String image;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "movie_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )

    private Set<Actor> actors = new HashSet<>();

    public Movie() {}

    public Movie(String title, int year, int duration, String country, String director, String genre, String synopsis, String image) {
        this.title = title;
        this.year = year;
        this.duration = duration;
        this.country = country;
        this.director = director;
        this.genre = genre;
        this.synopsis = synopsis;
        this.image = image;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getSynopsis() { return synopsis; }
    public void setSynopsis(String synopsis) { this.synopsis = synopsis; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Set<Actor> getActors() { return actors; }
    public void setActors(Set<Actor> actors) { this.actors = actors; }
}
