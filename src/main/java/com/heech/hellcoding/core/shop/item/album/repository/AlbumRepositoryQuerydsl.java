package com.heech.hellcoding.core.shop.item.album.repository;

import com.heech.hellcoding.core.shop.item.album.domain.Album;
import com.heech.hellcoding.core.shop.item.album.dto.AlbumSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlbumRepositoryQuerydsl {

    Page<Album> findAlbums(AlbumSearchCondition condition, Pageable pageable);
}
