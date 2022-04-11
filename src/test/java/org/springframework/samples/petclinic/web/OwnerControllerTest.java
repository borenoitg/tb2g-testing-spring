package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author : eanani
 * @project : tb2g-testing-spring
 * @created : 11/04/2022,
 **/
@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {

    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    @Autowired
    OwnerController ownerController;

    @Autowired
    ClinicService clinicService;

    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @AfterEach
    void tearDown() {
        reset(clinicService);
    }

    @Test
    void newOwnerPostValidTest() throws Exception {

        mockMvc.perform(post("/owners/new")
                        .param("firstName", "Jimmy")
                        .param("lastName", "Buffett")
                        .param("address", "123 Rue du JAVA")
                        .param("city", "Paris")
                        .param("telephone", "0645544533"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void findByNameNotFoundTest() throws Exception {

        mockMvc.perform(get("/owners").param("lastName", "Don't Find Me!"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void returnListOwnersTest() throws Exception {

        given(clinicService.findOwnerByLastName("")).willReturn(List.of(new Owner(), new Owner()));

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attributeExists("selections"));

        then(clinicService).should().findOwnerByLastName(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("");
    }

    @Test
    void findOwnerOneResultTest() throws Exception {

        Owner justOne = new Owner();
        justOne.setId(1);
        final String findJustOne = "Just One";
        justOne.setLastName(findJustOne);

        given(clinicService.findOwnerByLastName(findJustOne)).willReturn(List.of(justOne));

        mockMvc.perform(get("/owners")
                        .param("lastName", findJustOne))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/" + justOne.getId()));

        then(clinicService).should().findOwnerByLastName(stringArgumentCaptor.capture());

        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase(findJustOne);
    }

    @Test
    void initCreationFormTest() throws Exception {

        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEWS_OWNER_CREATE_OR_UPDATE_FORM))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().size(1));
    }
}