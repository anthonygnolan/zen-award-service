package com.coderdojo.zen.award;

import com.coderdojo.zen.award.model.Award;
import com.coderdojo.zen.award.model.Status;
import com.coderdojo.zen.award.repositories.AwardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;
import java.util.stream.StreamSupport;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AwardTests {

    @Autowired
    private AwardRepository awardRepository;

    // JUnit test for saveAward
    @Test
    @Order(1)
    @Rollback(value = false)
    void saveAwardTest(){

        Award award = new Award(1L,"MacBook Pro", Status.COMPLETED);

        awardRepository.save(award);

        Assertions.assertThat(award.getId()).isPositive();
    }

    @Test
    @Order(2)
    void getAwardTest(){

        Award award = awardRepository.findById(1L).get();

        Assertions.assertThat(award.getId()).isEqualTo(1L);

    }

    @Test
    @Order(3)
    void getListOfAwardsTest(){

        Iterable<Award> awards = awardRepository.findAll();

        Assertions.assertThat(StreamSupport.stream(awards.spliterator(), false).count()).isPositive();

    }

    @Test
    @Order(4)
    @Rollback(value = false)
    void updateAwardTest(){

        Award award = awardRepository.findById(1L).get();

        award.setDescription("New Category");
        award.setStatus(Status.COMPLETED);

        Award awardUpdated =  awardRepository.save(award);

        Assertions.assertThat(awardUpdated.getDescription()).isEqualTo("New Category");
        Assertions.assertThat(awardUpdated.getStatus()).isEqualTo(Status.COMPLETED);
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    void deleteAwardTest(){

        Award award = awardRepository.findById(1L).get();

        awardRepository.delete(award);

        //awardRepository.deleteById(1L);

        Award award1 = null;

        Optional<Award> optionalAward = awardRepository.findById(1L);

        if(optionalAward.isPresent()){
            award1 = optionalAward.get();
        }

        Assertions.assertThat(award1).isNull();
    }
}

