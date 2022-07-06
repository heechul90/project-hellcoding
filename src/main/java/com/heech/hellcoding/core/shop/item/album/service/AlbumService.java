package com.heech.hellcoding.core.shop.item.album.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.shop.item.album.domain.Album;
import com.heech.hellcoding.core.shop.item.album.dto.AlbumSearchCondition;
import com.heech.hellcoding.core.shop.item.album.repository.AlbumQueryRepository;
import com.heech.hellcoding.core.shop.item.album.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumQueryRepository albumQueryRepository;

    /**
     * 상품 > Album 목록 조회
     */
    public Page<Album> findAlbums(AlbumSearchCondition condition, Pageable pageable) {
        return albumQueryRepository.findAlbums(condition, pageable);
    }

    /**
     * 상품 > Album 단건 조회
     */
    public Album findAlbum(Long id) {
        return albumRepository.findById(id).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
    }

    /**
     * 상품 > Album 저장
     */
    @Transactional
    public Long saveAlbum(Album album) {
        return albumRepository.save(album).getId();
    }

    //TODO updateAlbum

    /**
     * 상품 > Album 삭제
     */
    @Transactional
    public void deleteAlbum(Album album) {
        albumRepository.delete(album);
    }
}
