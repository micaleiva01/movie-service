package com.example.movie_service.service;

import com.example.movie_service.model.Actor;
import com.example.movie_service.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorServiceImpl implements IActorService {

    private final ActorRepository actorRepository;

    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @Override
    public Actor saveActor(Actor actor) {
        return actorRepository.save(actor);
    }

    @Override
    public Actor getActorById(Long id) {
        return actorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actor con el id " + id + " no existe."));
    }

    @Override
    public void deleteActor(Long id) {
        if (actorRepository.existsById(id)) {
            actorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cannot delete. Actor not found with id: " + id);
        }
    }
}

