import { PagedApiResponse } from "../types/api";
import { Movie } from "../types/movie";
import { apiFetch } from "./client";

export async function getMovies(page?: number) {
  return await apiFetch<PagedApiResponse<Movie>>(
    `/api/movies?page=${page ?? 1}`
  );
}

export async function searchMovies(page: number, query: string) {
  return await apiFetch<PagedApiResponse<Movie>>(
    `/api/movies/search?page=${page}&query=${query}`
  );
}
