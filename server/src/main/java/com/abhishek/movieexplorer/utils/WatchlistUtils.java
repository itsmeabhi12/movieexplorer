package com.abhishek.movieexplorer.utils;

import java.util.HashMap;
import java.util.Map;

import com.abhishek.movieexplorer.model.WatchListItem;

public class WatchlistUtils {

    private WatchlistUtils() {

    }

    static public Map<String, Object> generateWatchlistDiff(WatchListItem previousObject, WatchListItem newObject) {
        Map<String, Object> diff = new HashMap<>();

        if (previousObject.getNotes() != null && !previousObject.getNotes().equals(newObject.getNotes())) {
            diff.put("notes", Map.of(
                    "previous", previousObject.getNotes(),
                    "new", newObject.getNotes()));
        }

        return diff;
    }

    public static WatchListItem cloneWatchlistItem(WatchListItem item) {
        return WatchListItem.builder()
                .id(item.getId())
                .user(item.getUser())
                .movie(item.getMovie())
                .notes(item.getNotes())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }
}
