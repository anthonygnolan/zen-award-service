package com.coderdojo.zen.award.repositories;

import com.coderdojo.zen.award.model.Award;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Hero is the main entity we'll be using to . . .
 * Please see the class for true identity
 * @author Captain America
 */
public interface AwardRepository extends JpaRepository<Award, Long> {
}
