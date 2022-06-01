package com.heech.hellcoding.core.shop.item.album.domain;

import com.heech.hellcoding.core.shop.item.info.domain.Item;
import lombok.Builder;
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

    @Builder
    public Album(String name, String title, String content, int price, int stockQuantity, String artist) {
        super(name, title, content, price, stockQuantity);
        this.artist = artist;
    }
}

