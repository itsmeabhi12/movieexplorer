import { Movie } from "./movie";

export interface WatchListItem {
  id: string;
  movie: Movie;
  notes?: string | null;
  created_at: Date;
  updated_at?: Date;
}

export interface CreateWatchListItemPayload {
  tmdb_movie_id: number;
  notes?: string;
}

export interface UpdateWatchListItemPayload {
  notes?: string;
}
