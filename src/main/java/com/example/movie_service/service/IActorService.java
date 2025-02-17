package com.example.movie_service.service;

import com.example.movie_service.model.Actor;

import java.util.List;

public interface IActorService {
    List<Actor> getAllActors();

    Actor saveActor(Actor actor);

    Actor getActorById(Long id);

    void deleteActor(Long id);
}

