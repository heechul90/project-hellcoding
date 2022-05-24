package com.heech.hellcoding.core.shop.item.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ALBUM")
@Getter
@NoArgsConstructor
public class Album extends Item {

    private String artist;
}

