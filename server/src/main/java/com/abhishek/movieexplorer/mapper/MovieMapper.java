package com.abhishek.movieexplorer.mapper;

import org.mapstruct.Mapper;

import com.abhishek.movieexplorer.dto.response.MovieDTO;
import com.abhishek.movieexplorer.model.Movie;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDTO toDto(Movie movie);

    Movie toEntity(MovieDTO dto);

}