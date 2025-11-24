package com.abhishek.movieexplorer.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abhishek.movieexplorer.model.WatchListItem;

@Repository
public interface WatchListRepository extends JpaRepository<WatchListItem, UUID> {
    Optional<WatchListItem> findByUser_IdAndMovie_Id(UUID userId, Long movieId);

    Optional<WatchListItem> findByUser_IdAndId(UUID userId, UUID id);

    boolean existsByUser_IdAndMovie_Id(UUID userId, Long movieId);

    List<WatchListItem> findByUser_Id(UUID userId);

    List<WatchListItem> findByMovie_Id(Long movieId);
}
