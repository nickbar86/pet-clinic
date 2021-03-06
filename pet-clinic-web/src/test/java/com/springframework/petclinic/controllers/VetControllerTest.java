package com.springframework.petclinic.controllers;

import com.jayway.jsonpath.JsonPath;
import com.springframework.petclinic.model.Vet;
import com.springframework.petclinic.services.VetService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("VetController Unit Test")
class VetControllerTest {

    @Mock
    VetService vetService;

    @InjectMocks
    VetController controller;

    Set<Vet> vets;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        vets = new HashSet<>();
        vets.add(Vet.builder().id(1L).build());
        vets.add(Vet.builder().id(2L).build());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listVets() throws Exception {
        Mockito.when(vetService.findAll()).thenReturn(vets);
        mockMvc.perform(MockMvcRequestBuilders.get("/vets"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("vets/index"))
                .andExpect(MockMvcResultMatchers.model().attribute("vets", Matchers.hasSize(2)));
    }

    @Test
    void getVetsJson() throws Exception {
        Mockito.when(vetService.findAll()).thenReturn(vets);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vets"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(2)));
    }

    @Test
    void getEmptyVetsJson() throws Exception {
        Mockito.when(vetService.findAll()).thenReturn(new HashSet<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/vets"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(0)));
    }
}