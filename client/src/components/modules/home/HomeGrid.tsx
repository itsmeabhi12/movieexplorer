"use client";

import { getMovies, searchMovies } from "@/src/api/movie";
import { addToWatchList } from "@/src/api/watchlist";
import { useAuth } from "@/src/lib/use-auth";
import { Movie } from "@/src/types/movie";
import { useRouter } from "next/navigation";
import { useEffect, useRef, useState } from "react";
import toast from "react-hot-toast";
import CircleButton from "../../common/CircleButton";
import { CircularProgressIndicator } from "../../common/CircularProgressIndicator";
import InputDialog from "../../common/InputDialog";
import { MovieCard } from "../../common/MovieCard";
import { PrimaryButton } from "../../common/PrimaryButton";
import { SearchBar } from "../../common/SearchBar";
import { AuthDialog } from "./AuthDialog";

export function HomeGrid({ initialMovies }: { initialMovies: Movie[] }) {
  const [movies, setMovies] = useState(initialMovies);
  const [isLoading, setLoading] = useState(false);
  const [isLoadingMore, setLoadingMore] = useState(false);
  const { isAuthenticated, checkAuthenticatedStatus } = useAuth();
  const [isAuthOpen, setAuthOpen] = useState(false);
  const [isDialogOpen, setDialogOpen] = useState(false);
  const [selected, setSelected] = useState<Movie | null>(null);
  const [query, setQuery] = useState<string | null>(null);

  const firstLoad = useRef(true);
  const router = useRouter();

  async function handleSearch(query: string) {
    setLoading(true);

    const { data, error } = query.trim()
      ? await searchMovies(1, query)
      : await getMovies(1);
    if (data) {
      setMovies(data.results);
    } else if (error) {
      toast.error(error.message);
    }
    setLoading(false);
  }

  useEffect(() => {
    if (query === null) return;
    if (firstLoad.current) {
      firstLoad.current = false;
      return;
    }
    handleSearch(query ?? "");
  }, [query]);

  async function handleAddToWatchlist(movie: Movie, notes: string) {
    if (isAuthenticated) {
      addToWatchList(movie, notes).then((result) => {
        if (result.error) {
          toast.error("Failed to add to watchlist: " + result.error.message);
          return;
        }
        toast.success("Added to watchlist");
      });
    } else {
      toast.error("Please login first");
    }
  }

  async function loadMore() {
    setLoadingMore(true);
    const { data, error } = query?.trim()
      ? await searchMovies(Math.floor(movies.length / 20) + 1, query)
      : await getMovies(Math.floor(movies.length / 20) + 1);
    if (data) {
      setMovies((prev) => [...prev, ...data.results]);
    } else if (error) {
      toast.error("Failed to load more: " + error.message);
    }
    setLoadingMore(false);
  }

  return (
    <div>
      <div className="flex justify-between p-4">
        <div></div>
        <SearchBar onSearch={(e) => setQuery(e)}></SearchBar>

        <CircleButton
          icon={"🙍🏻‍♂️"}
          online={isAuthenticated}
          onClick={() => {
            if (!isAuthenticated) {
              setAuthOpen(true);
            } else {
              router.push("/watchlist");
            }
          }}
        />
      </div>
      <div className="flex flex-wrap justify-center content-center gap-4 p-4">
        {isLoading ? (
          <CircularProgressIndicator />
        ) : (
          movies.map((e) => (
            <MovieCard
              key={e.id}
              id={e.id}
              title={e.title}
              posterPath={e.poster_path}
              onClick={(_) => {
                setSelected(e);
                setDialogOpen(true);
              }}
            ></MovieCard>
          ))
        )}
      </div>
      {!isLoading && movies.length !== 0 && (
        <div className=" flex justify-center mb-4">
          <PrimaryButton
            title="LoadMore"
            isLoading={isLoadingMore}
            onTap={() => {
              loadMore();
            }}
          />
        </div>
      )}
      <AuthDialog
        isOpen={isAuthOpen}
        onClose={function () {
          checkAuthenticatedStatus();
          return setAuthOpen(false);
        }}
      />

      <InputDialog
        isOpen={isDialogOpen}
        onClose={() => setDialogOpen(false)}
        onSave={(note) => handleAddToWatchlist(selected!, note)}
      />
    </div>
  );
}
