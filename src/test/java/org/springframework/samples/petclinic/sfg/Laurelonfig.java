package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author : eanani
 * @project : tb2g-testing-spring
 * @created : 07/04/2022,
 **/
@Profile("base-test")
@Configuration
public class Laurelonfig {

    @Bean
    LaurelWordProducer laurelWordProducer() {
        return new LaurelWordProducer();
    }
}

