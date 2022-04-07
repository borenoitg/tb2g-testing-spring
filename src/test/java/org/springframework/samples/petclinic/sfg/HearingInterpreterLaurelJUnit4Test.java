package org.springframework.samples.petclinic.sfg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author : eanani
 * @project : tb2g-testing-spring
 * @created : 07/04/2022,
 **/
@ActiveProfiles("base-test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BaseConfig.class, Laurelonfig.class})
public class HearingInterpreterLaurelJUnit4Test {

    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    public void whatIheard() {
        String word = hearingInterpreter.whatIheard();

        assertEquals("Laurel", word);
    }
}