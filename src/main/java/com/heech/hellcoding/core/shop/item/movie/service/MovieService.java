package com.heech.hellcoding.core.shop.item.movie.service;

import com.heech.hellcoding.core.common.exception.NoSuchElementException;
import com.heech.hellcoding.core.shop.item.movie.domain.Movie;
import com.heech.hellcoding.core.shop.item.movie.dto.MovieSearchCondition;
import com.heech.hellcoding.core.shop.item.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;

    /**
     * 상품 > Movie 목록 조회
     */
    public Page<Movie> findMovies(MovieSearchCondition condition, Pageable pageable) {
        return movieRepository.findMovies(condition, pageable);
    }

    /**
     * 상품 > Movie 단건 조회
     */
    public Movie findMovie(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new NoSuchElementException("조회에 실패했습니다."));
    }

    /**
     * 상품 > Movie 저장
     */
    @Transactional
    public Long saveMovie(Movie movie) {
        return movieRepository.save(movie).getId();
    }

    //TODO updateMovie

    /**
     * 상품 > Movie 삭제
     */
    @Transactional
    public void deleteMovie(Movie movie) {
        movieRepository.delete(movie);
    }
}
