package org.springframework.samples.petclinic.sfg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author : eanani
 * @project : tb2g-testing-spring
 * @created : 07/04/2022,
 **/
class HearingInterpreterTest {

    HearingInterpreter hearingInterpreter;

    @BeforeEach
    void setUp() {
        hearingInterpreter = new HearingInterpreter(new LaurelWordProducer());
    }

    @Test
    void whatIheard() {
        String word = hearingInterpreter.whatIheard();

        assertEquals("Laurel", word);
    }
}