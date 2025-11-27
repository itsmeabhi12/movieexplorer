"use client";

import { getMovies, searchMovies } from "@/src/api/movie";
import { addToWatchList } from "@/src/api/watchlist";
import { useAuth } from "@/src/lib/use-auth";
import { Movie } from "@/src/types/movie";
import { useRouter } from "next/navigation";
import { useCallback, useEffect, useRef, useState } from "react";
import toast from "react-hot-toast";
import CircleButton from "../../common/CircleButton";
import { CircularProgressIndicator } from "../../common/CircularProgressIndicator";
import InputDialog from "../../common/InputDialog";
import { MovieCard } from "../../common/MovieCard";
import { PrimaryButton } from "../../common/PrimaryButton";
import { SearchBar } from "../../common/SearchBar";
import { AuthDialog } from "./AuthDialog";

const PAGE_SIZE = 20;

export function HomeGrid({ initialMovies }: { initialMovies: Movie[] }) {
  const router = useRouter();
  const { isAuthenticated, checkAuthenticatedStatus } = useAuth();

  const [movies, setMovies] = useState<Movie[]>(initialMovies);
  const [isLoading, setIsLoading] = useState(false);
  const [isLoadingMore, setIsLoadingMore] = useState(false);
  const [isAuthOpen, setIsAuthOpen] = useState(false);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [selectedMovie, setSelectedMovie] = useState<Movie | null>(null);
  const [query, setQuery] = useState<string | null>(null);
  const [hasReachedEnd, setHasReachedEnd] = useState(false);

  const isFirstQueryChange = useRef(true);

  const fetchMoviesPage = useCallback(
    async (page: number, q?: string | null) => {
      const trimmed = (q ?? "").trim();
      return trimmed ? searchMovies(page, trimmed) : getMovies(page);
    },
    []
  );

  const handleSearch = useCallback(
    async (q: string) => {
      setIsLoading(true);
      try {
        const { data, error } = await fetchMoviesPage(1, q);
        if (error) {
          toast.error(error.message);
          return;
        }
        if (data) {
          setMovies(data.results);
          setHasReachedEnd(data.results.length < PAGE_SIZE);
        }
      } finally {
        setIsLoading(false);
      }
    },
    [fetchMoviesPage]
  );

  useEffect(() => {
    if (query === null) return;
    if (isFirstQueryChange.current) {
      isFirstQueryChange.current = false;
      return;
    }
    void handleSearch(query ?? "");
  }, [query, handleSearch]);

  const handleProfileClick = useCallback(() => {
    if (!isAuthenticated) {
      setIsAuthOpen(true);
    } else {
      router.push("/watchlist");
    }
  }, [isAuthenticated, router]);

  const handleMovieClick = useCallback(
    (movie: Movie) => {
      if (!isAuthenticated) {
        toast.error("Please login first");
        return;
      }
      setSelectedMovie(movie);
      setIsDialogOpen(true);
    },
    [isAuthenticated]
  );

  const handleAddToWatchlist = useCallback(
    async (movie: Movie, notes: string) => {
      if (!isAuthenticated) {
        toast.error("Please login first");
        return;
      }
      const result = await addToWatchList(movie, notes);
      if (result.error) {
        toast.error("Failed to add to watchlist: " + result.error.message);
        return;
      }
      toast.success("Added to watchlist");
    },
    [isAuthenticated]
  );

  const handleSaveNotes = useCallback(
    async (note: string) => {
      if (!selectedMovie) {
        toast.error("No movie selected");
        return;
      }
      await handleAddToWatchlist(selectedMovie, note);
      setIsDialogOpen(false);
    },
    [handleAddToWatchlist, selectedMovie]
  );

  const loadMore = useCallback(async () => {
    if (hasReachedEnd || isLoadingMore) return;
    setIsLoadingMore(true);
    try {
      const nextPage = Math.floor(movies.length / PAGE_SIZE) + 1;
      const { data, error } = await fetchMoviesPage(nextPage, query);
      if (error) {
        toast.error("Failed to load more: " + error.message);
        return;
      }
      if (data) {
        setMovies((prev) => [...prev, ...data.results]);
        setHasReachedEnd(data.results.length < PAGE_SIZE);
      }
    } finally {
      setIsLoadingMore(false);
    }
  }, [fetchMoviesPage, hasReachedEnd, isLoadingMore, movies.length, query]);

  const handleAuthClose = useCallback(() => {
    checkAuthenticatedStatus();
    setIsAuthOpen(false);
  }, [checkAuthenticatedStatus]);

  const handleDialogClose = useCallback(() => {
    setIsDialogOpen(false);
  }, []);

  const handleSearchInput = useCallback((value: string) => {
    setQuery(value);
  }, []);

  return (
    <div>
      <div className="flex justify-between p-4">
        <div />
        <SearchBar onSearch={handleSearchInput} />
        <CircleButton
          icon="🙍🏻‍♂️"
          online={isAuthenticated}
          onClick={handleProfileClick}
        />
      </div>

      <div className="grid p-10 gap-10 grid-cols-[repeat(auto-fill,minmax(180px,1fr))] place-items-center">
        {isLoading ? (
          <div className="col-span-full">
            <CircularProgressIndicator />
          </div>
        ) : (
          movies.map((m) => (
            <MovieCard
              key={m.id}
              id={m.id}
              title={m.title}
              posterPath={m.poster_path}
              onClick={() => handleMovieClick(m)}
            />
          ))
        )}
      </div>

      {!isLoading && movies.length !== 0 && !hasReachedEnd && (
        <div className="flex justify-center mb-4">
          <PrimaryButton
            title="LoadMore"
            isLoading={isLoadingMore}
            onTap={loadMore}
          />
        </div>
      )}

      <AuthDialog isOpen={isAuthOpen} onClose={handleAuthClose} />

      <InputDialog
        isOpen={isDialogOpen}
        onClose={handleDialogClose}
        onSave={handleSaveNotes}
      />
    </div>
  );
}
