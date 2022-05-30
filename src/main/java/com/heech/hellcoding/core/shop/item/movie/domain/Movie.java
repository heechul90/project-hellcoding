package com.heech.hellcoding.core.shop.item.movie.domain;

import com.heech.hellcoding.core.shop.item.common.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "MOVIE")
@Getter
@NoArgsConstructor
public class Movie extends Item {

    private String director;
    private String actor;

    @Builder
    public Movie(String name, String title, String content, int price, int stockQuantity, String director, String actor) {
        super(name, title, content, price, stockQuantity);
        this.director = director;
        this.actor = actor;
    }
}
