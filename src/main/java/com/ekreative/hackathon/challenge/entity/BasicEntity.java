package com.ekreative.hackathon.challenge.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicEntity {
    private Integer id;

    public boolean isNew() {
        return id == null;
    }
}
