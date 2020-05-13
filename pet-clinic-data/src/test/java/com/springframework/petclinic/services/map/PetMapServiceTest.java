package com.springframework.petclinic.services.map;

import com.springframework.petclinic.model.Owner;
import com.springframework.petclinic.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PetMapServiceTest {

    public static final long ID = 1L;
    PetMapService petMapService;

    @BeforeEach
    void setUp() {
        petMapService = new PetMapService();
        petMapService.save(Pet.builder().id(ID).name("Nick").build());
    }

    @Test
    void findAll() {
        assertEquals(1, petMapService.findAll().size());
    }

    @Test
    void findById() {
        assertEquals(ID, petMapService.findById(ID).getId());
    }

    @Test
    void saveExistingId() {
        Pet pet2 = Pet.builder().id(2L).name("Maria").build();
        assertEquals(2L, petMapService.save(pet2).getId());
    }

    @Test
    void saveNoId() {
        Pet pet = petMapService.save(Pet.builder().build());
        assertNotNull(pet);
        assertNotNull(pet.getId());
    }

    @Test
    void delete() {
        petMapService.delete(petMapService.findById(ID));
        assertEquals(0, petMapService.findAll().size());
    }

    @Test
    void deleteById() {
        petMapService.deleteById(ID);
        assertEquals(0, petMapService.findAll().size());
    }
}