import { Movie } from "../types/movie";
import { WatchListItem } from "../types/watchlist";
import { apiFetch } from "./client";

export default async function getWatchList() {
  return await apiFetch<WatchListItem[]>("/api/watchlist");
}

export async function addToWatchList(movie: Movie, notes?: string) {
  return await apiFetch<WatchListItem>("/api/watchlist", {
    method: "POST",
    body: JSON.stringify({
      tmdb_movie_id: movie.id,
      notes: notes,
    }),
  });
}

export async function removeFromWatchList(watchListItemId: string) {
  return await apiFetch<null>(`/api/watchlist/${watchListItemId}`, {
    method: "DELETE",
  });
}

export async function updateWatchListItem(
  watchListItemId: string,
  notes: string
) {
  return await apiFetch<WatchListItem>(`/api/watchlist/${watchListItemId}`, {
    method: "PATCH",
    body: JSON.stringify({ notes }),
  });
}
