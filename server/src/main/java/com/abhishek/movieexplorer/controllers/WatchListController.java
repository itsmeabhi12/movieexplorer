package com.abhishek.movieexplorer.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.abhishek.movieexplorer.dto.request.AddToWatchListRequest;
import com.abhishek.movieexplorer.dto.request.UpdateWatchListRequest;
import com.abhishek.movieexplorer.dto.response.WatchListItemDTO;
import com.abhishek.movieexplorer.service.WatchListService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/watchlist")
@RequiredArgsConstructor
public class WatchListController {

    private final WatchListService watchListService;

    @GetMapping
    public ResponseEntity<List<WatchListItemDTO>> getWatchlist(@AuthenticationPrincipal UUID userId) {
        return ResponseEntity.ok(watchListService.getAllWatchListItems(userId));
    }

    @PostMapping
    public ResponseEntity<WatchListItemDTO> addToWatchList(
            @AuthenticationPrincipal UUID userId,
            @Valid @RequestBody AddToWatchListRequest request) {
        return ResponseEntity.ok(watchListService.addToWatchList(userId, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<WatchListItemDTO> updateWatchListItem(
            @AuthenticationPrincipal UUID userId,
            @PathVariable UUID id,
            @RequestBody UpdateWatchListRequest request) {
        return ResponseEntity.ok(watchListService.updateWatchListItem(userId, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFromWatchlist(
            @AuthenticationPrincipal UUID userId,
            @PathVariable UUID id) {
        watchListService.deleteFromWatchList(userId, id);
        return ResponseEntity.noContent().build();
    }

}
