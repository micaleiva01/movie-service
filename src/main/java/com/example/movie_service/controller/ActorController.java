package com.example.movie_service.controller;

import com.example.movie_service.model.Actor;
import com.example.movie_service.service.IActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/actors")
public class ActorController {

    private final IActorService actorService;

    @Autowired
    public ActorController(IActorService actorService) {
        this.actorService = actorService;
    }


    @GetMapping
    public List<Actor> getActors() {
        return actorService.getAllActors();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Actor addActor(@RequestBody Actor actor) {
        return actorService.saveActor(actor);
    }

    @GetMapping("/{id}")
    public Actor getActor(@PathVariable Long id) {
        return actorService.getActorById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable Long id, @RequestBody Actor updatedActor) {
        Actor actor = actorService.getActorById(id);
        if (actor == null) {
            return ResponseEntity.notFound().build();
        }

        actor.setName(updatedActor.getName());
        actor.setBirthdate(updatedActor.getBirthdate());
        actor.setNationality(updatedActor.getNationality());

        Actor savedActor = actorService.saveActor(actor);
        return ResponseEntity.ok(savedActor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActor(@PathVariable Long id) {
        actorService.deleteActor(id);
        return ResponseEntity.ok("Actor eliminado correctamente!");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handleOptions() {
        return ResponseEntity.ok().build();
    }
}
