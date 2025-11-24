package com.abhishek.movieexplorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhishek.movieexplorer.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
