package com.springframework.petclinic.controllers;

import com.springframework.petclinic.model.Owner;
import com.springframework.petclinic.model.Pet;
import com.springframework.petclinic.model.PetType;
import com.springframework.petclinic.services.PetService;
import com.springframework.petclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
    private static final String PETS_CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";
    private static final String VISIT_DESCIPTION = "description test";


    @Mock
    PetService petService;

    @Mock
    VisitService visitService;

    @InjectMocks
    VisitController visitController;

    private MockMvc mockMvc;

    private final UriTemplate visitUriTemplate = new UriTemplate("/owners/{ownerId}/pets/{petId}/visits/new");
    private final Map<String, String> uriVariables = new HashMap<>();
    private URI visitsUri;

    @BeforeEach
    void setUp() {
        Owner petOwner = Owner.builder().id(1L).firstName("Nick").lastName("Bar").build();
        Pet pet = Pet.builder()
                .id(1L)
                .birthDate(LocalDate.of(2018, 8, 8))
                .name("piris")
                .visits(new HashSet<>())
                .owner(petOwner)
                .petType(PetType.builder().id(1L).name("CAT").build())
                .build();
        when(petService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(pet);
        uriVariables.clear();
        uriVariables.put("ownerId", petOwner.getId().toString());
        uriVariables.put("petId", pet.getId().toString());
        visitsUri = visitUriTemplate.expand(uriVariables);
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }

    @Test
    void initNewVisitForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(visitsUri))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(PETS_CREATE_OR_UPDATE_VISIT_FORM));
    }

    @Test
    void processNewVisitForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(visitsUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("date", "2018-08-08")
                .param("description", "VISIT_DESCIPTION"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/{ownerId}"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("visit"));
    }
}