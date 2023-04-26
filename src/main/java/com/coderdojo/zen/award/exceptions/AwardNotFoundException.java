package com.coderdojo.zen.award.exceptions;

/**
 * Hero is the main entity we'll be using to . . .
 * Please see the class for true identity
 * @author Captain America
 */
public class AwardNotFoundException extends RuntimeException {
    /**
     * Hero is the main entity we'll be using to . . .
     * Please see the class for true identity
     * @author Captain America
     */
    public AwardNotFoundException(Long id) {
        super("Could not find award " + id);
    }
}
