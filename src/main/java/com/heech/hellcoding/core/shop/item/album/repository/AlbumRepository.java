package com.heech.hellcoding.core.shop.item.album.repository;

import com.heech.hellcoding.core.shop.item.album.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long>, AlbumRepositoryQuerydsl {

    List<Album> findByName(String name);
}
