package com.coderdojo.zen.award.controllers;

import com.coderdojo.zen.award.exceptions.AwardModelAssembler;
import com.coderdojo.zen.award.exceptions.AwardNotFoundException;
import com.coderdojo.zen.award.repositories.AwardRepository;
import com.coderdojo.zen.award.model.Status;
import com.coderdojo.zen.award.model.Award;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
/**
 * Returns an Image object that can then be painted on the screen.
 * The url argument must specify an absolute. The name
 * argument is a specifier that is relative to the url argument.
 * <p>
 * This method always returns immediately, whether the
 * image exists. When this applet attempts to draw the image on
 * the screen, the data will be loaded. The graphics primitives
 * that draw the image will incrementally paint on the screen.
 *
 */
@RestController
@RequestMapping("/api/v1/awards")
public class AwardController {
    private final AwardRepository awardRepository;
    private final AwardModelAssembler assembler;

    AwardController(AwardRepository awardRepository, AwardModelAssembler assembler) {

        this.awardRepository = awardRepository;
        this.assembler = assembler;
    }
    /**
     * Hero is the main entity we'll be using to . . .
     * Please see the class for true identity
     * @author Captain America
     * @return EntityModel
     */
    @GetMapping
    public CollectionModel<EntityModel<Award>> all() {

        List<EntityModel<Award>> awards = awardRepository.findAll().stream() //
                .map(assembler::toModel) //
                .toList();

        return CollectionModel.of(awards, //
                linkTo(methodOn(AwardController.class).all()).withSelfRel());
    }

    @PostMapping
    ResponseEntity<EntityModel<Award>> newAward(@RequestBody Award award) {

        award.setStatus(Status.IN_PROGRESS);
        Award newAward = awardRepository.save(award);

        return ResponseEntity //
                .created(linkTo(methodOn(AwardController.class).one(newAward.getId())).toUri()) //
                .body(assembler.toModel(newAward));
    }
    /**
     * <p>This is a simple description of the method. . .
     * <a href="http://www.supermanisthegreatest.com">Superman!</a>
     * </p>
     * @param id the amount of incoming damage
     * @return the amount of health hero has after attack
     * @see <a href="http://www.link_to_jira/HERO-402">HERO-402</a>
     * @since 1.0
     */
    @GetMapping("/{id}")
    public EntityModel<Award> one(@PathVariable Long id) {

        Award award = awardRepository.findById(id) //
                .orElseThrow(() -> new AwardNotFoundException(id));

        return assembler.toModel(award);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> cancel(@PathVariable Long id) {

        Award award = awardRepository.findById(id) //
                .orElseThrow(() -> new AwardNotFoundException(id));

        if (award.getStatus() == Status.IN_PROGRESS) {
            award.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(awardRepository.save(award)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed") //
                        .withDetail("You can't cancel an award that is in the " + award.getStatus() + " status"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> complete(@PathVariable Long id) {

        Award award = awardRepository.findById(id) //
                .orElseThrow(() -> new AwardNotFoundException(id));

        if (award.getStatus() == Status.IN_PROGRESS) {
            award.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(awardRepository.save(award)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed") //
                        .withDetail("You can't complete an award that is in the " + award.getStatus() + " status"));
    }
}
