package com.heech.hellcoding.core.shop.item.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "MOVIE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie extends Item {

    private String director;
    private String actor;
}
