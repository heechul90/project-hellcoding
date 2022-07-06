package com.heech.hellcoding.core.shop.item.movie.repository;

import com.heech.hellcoding.core.shop.item.movie.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByName(String name);
}
