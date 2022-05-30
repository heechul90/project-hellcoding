package com.heech.hellcoding.core.shop.item.movie.repository;

import com.heech.hellcoding.core.shop.item.movie.domain.Movie;
import com.heech.hellcoding.core.shop.item.movie.dto.MovieSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieRepositoryQuerydsl {

    Page<Movie> findMovies(MovieSearchCondition condition, Pageable pageable);
}
