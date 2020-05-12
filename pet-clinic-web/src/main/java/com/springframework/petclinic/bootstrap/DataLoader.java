package com.springframework.petclinic.bootstrap;

import com.springframework.petclinic.model.*;
import com.springframework.petclinic.services.*;
import com.springframework.petclinic.services.map.OwnerServiceMap;
import com.springframework.petclinic.services.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = petTypeService.findAll().size();
        if (count == 0) loadData();
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDodPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCayPetType = petTypeService.save(cat);

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality saveRadiology = specialityService.save(radiology);
        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");
        Speciality savedSurgery = specialityService.save(surgery);
        Speciality dentistry = new Speciality();
        dentistry.setDescription("Dentistry");
        Speciality savedDentistry = specialityService.save(dentistry);


        Owner owner1 = new Owner();
        owner1.setFirstName("Nick");
        owner1.setLastName("Bartsotas");
        owner1.setAddress("dsadas, 16");
        owner1.setCity("Athens");
        owner1.setTelephone("123");

        Pet nicksPet = new Pet();
        nicksPet.setPetType(dog);
        nicksPet.setOwner(owner1);
        nicksPet.setBirthDate(LocalDate.now());
        nicksPet.setName("Mitsos");
        owner1.getPets().add(nicksPet);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Maria");
        owner2.setLastName("Aristeidou");
        owner2.setAddress("dsadas, 16");
        owner2.setCity("Athens");
        owner2.setTelephone("123");

        Pet mariaPet = new Pet();
        mariaPet.setPetType(cat);
        mariaPet.setOwner(owner2);
        mariaPet.setBirthDate(LocalDate.now());
        mariaPet.setName("Mitsena");
        owner2.getPets().add(mariaPet);

        ownerService.save(owner2);

        System.out.println("Loading Owners...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Bob");
        vet1.setLastName("Dylan");
        vet1.getSpecialties().add(saveRadiology);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Patty");
        vet2.setLastName("Smith");
        vet2.getSpecialties().add(savedSurgery);
        vetService.save(vet2);


        System.out.println("Loading Vets...");
    }
}
