package com.JavaFinal.ToiletsFinder.models;

import jakarta.persistence.*;


public class LocationEntity {
    @Id
    @GeneratedValue
    private Long id;



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
