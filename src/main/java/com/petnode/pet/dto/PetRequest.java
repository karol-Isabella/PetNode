package com.petnode.pet.dto;

public class PetRequest {
    private String name;
    private String breed;
    private Integer age;
    private String gender;
    private String ownerId;

    public PetRequest() {}

    public PetRequest(String name, String breed, Integer age, String gender, String ownerId) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
