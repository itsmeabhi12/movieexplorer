package com.abhishek.movieexplorer.mapper;

import org.mapstruct.Mapper;

import com.abhishek.movieexplorer.dto.response.WatchListItemDTO;
import com.abhishek.movieexplorer.model.WatchListItem;

@Mapper(componentModel = "spring")
public interface WatchlistMapper {

    WatchListItemDTO toDto(WatchListItem watchListItem);

}
