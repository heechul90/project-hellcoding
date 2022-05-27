package com.heech.hellcoding.core.shop.item.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Movie")
@Getter
@NoArgsConstructor
public class Movie extends Item {

    private String director;
    private String actor;
}
