package com.heech.hellcoding.core.shop.item.album.domain;

import com.heech.hellcoding.core.shop.item.common.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ALBUM")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album extends Item {

    private String artist;
}

