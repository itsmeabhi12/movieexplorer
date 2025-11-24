package com.abhishek.movieexplorer.service;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abhishek.movieexplorer.dto.request.AddToWatchListRequest;
import com.abhishek.movieexplorer.dto.request.UpdateWatchListRequest;
import com.abhishek.movieexplorer.dto.response.MovieDTO;
import com.abhishek.movieexplorer.dto.response.WatchListItemDTO;
import com.abhishek.movieexplorer.exception.DuplicateResourceException;
import com.abhishek.movieexplorer.exception.ResourceNotFoundException;
import com.abhishek.movieexplorer.mapper.MovieMapper;
import com.abhishek.movieexplorer.mapper.WatchlistMapper;
import com.abhishek.movieexplorer.model.AuditLog;
import com.abhishek.movieexplorer.model.Movie;
import com.abhishek.movieexplorer.model.User;
import com.abhishek.movieexplorer.model.WatchListItem;
import com.abhishek.movieexplorer.repository.MovieRepository;
import com.abhishek.movieexplorer.repository.UserRepository;
import com.abhishek.movieexplorer.repository.WatchListRepository;
import com.abhishek.movieexplorer.utils.WatchlistUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WatchListService {

    private final WatchListRepository watchListRepository;
    private final UserRepository userRepository;
    private final TmdbService tmdbService;
    private final AuditLogService auditLogService;
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final WatchlistMapper watchlistMapper;

    @Transactional
    public WatchListItemDTO addToWatchList(@NonNull UUID userId, AddToWatchListRequest request) {

        if (watchListRepository.existsByUser_IdAndMovie_Id(userId, request.getTmdbMovieId())) {
            throw new DuplicateResourceException("Movie is already on the watchlist");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        MovieDTO movieDTO = tmdbService.getMovie(request.getTmdbMovieId());

        Movie movie = movieMapper.toEntity(movieDTO);

        Movie cachedMovie = movieRepository.findById(movie.getId()).orElse(null);
        if (cachedMovie == null) {
            cachedMovie = movieRepository.save(movie);
        }

        WatchListItem watchListItem = WatchListItem.builder()
                .user(user)
                .movie(cachedMovie)
                .notes(request.getNotes())
                .build();

        WatchListItem savedWatchListItem = watchListRepository.save(watchListItem);
        auditLogService.logCreateAction(savedWatchListItem, userId, AuditLog.TargetType.WATCHLIST_ITEM,
                savedWatchListItem.getMovie().getId().toString());
        return watchlistMapper.toDto(savedWatchListItem);
    }

    @Transactional(readOnly = true)
    public List<WatchListItemDTO> getAllWatchListItems(UUID userId) {
        List<WatchListItem> items = watchListRepository.findByUser_Id(userId);
        return items.stream()
                .map(watchlistMapper::toDto)
                .toList();
    }

    @Transactional
    public WatchListItemDTO updateWatchListItem(UUID userId, UUID watchListItemId, UpdateWatchListRequest request) {
        WatchListItem item = watchListRepository.findByUser_IdAndId(userId, watchListItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Watchlist item not found"));

        WatchListItem previousItem = WatchlistUtils.cloneWatchlistItem(item);

        if (request.getNotes() != null && !request.getNotes().isBlank()) {
            item.setNotes(request.getNotes());
        }
        auditLogService.logUpdateAction(previousItem, item, userId, AuditLog.TargetType.WATCHLIST_ITEM,
                item.getMovie().getId().toString(), WatchlistUtils.generateWatchlistDiff(previousItem, item));

        WatchListItem updatedItem = watchListRepository.save(item);
        return watchlistMapper.toDto(updatedItem);
    }

    @Transactional
    public void deleteFromWatchList(UUID userId, UUID id) {
        WatchListItem item = watchListRepository.findByUser_IdAndId(userId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Watchlist item not found"));

        List<WatchListItem> itemsWithMovie = watchListRepository.findByMovie_Id(item.getMovie().getId());

        auditLogService.logDeleteAction(item, userId, AuditLog.TargetType.WATCHLIST_ITEM,
                item.getMovie().getId().toString());

        watchListRepository.delete(item);

        if (itemsWithMovie.size() == 1) {
            movieRepository.delete(item.getMovie());
        }
    }
}