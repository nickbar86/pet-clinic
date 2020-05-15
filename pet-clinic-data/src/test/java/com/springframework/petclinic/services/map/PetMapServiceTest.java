package com.springframework.petclinic.services.map;

import com.springframework.petclinic.model.Owner;
import com.springframework.petclinic.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PetMapServiceTest {

    public final long ID = 1L;
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
    void findByExistingId() {
        assertEquals(ID, petMapService.findById(ID).getId());
    }

    @Test
    void findByNotExistingId() {
        assertNull(petMapService.findById(5L));
    }

    @Test
    void findByNullId() {
        assertNull(petMapService.findById(null));
    }

    @Test
    void saveExistingId() {
        Pet pet2 = Pet.builder().id(2L).name("Maria").build();
        assertEquals(2L, petMapService.save(pet2).getId());
    }

    @Test
    void saveUpdateId() {
        Pet pet1 = petMapService.save(Pet.builder().id(1L).name("Maria").build());
        assertEquals(1L, pet1.getId());
        assertEquals("Maria", pet1.getName());
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

    @Test
    void deleteByNonExistingId() {
        petMapService.deleteById(5L);
        assertEquals(1, petMapService.findAll().size());
    }

    @Test
    void deleteByNullId() {
        petMapService.deleteById(null);
        assertEquals(1, petMapService.findAll().size());
    }
}