"use client";

import getWatchList, {
  removeFromWatchList,
  updateWatchListItem,
} from "@/src/api/watchlist";
import { WatchListItem } from "@/src/types/watchlist";

import { useAuth } from "@/src/lib/use-auth";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import CircleButton from "../../common/CircleButton";
import { CircularProgressIndicator } from "../../common/CircularProgressIndicator";
import InputDialog from "../../common/InputDialog";
import { MovieCard } from "../../common/MovieCard";

export default function Watchlist() {
  const [watchlist, setWatchlist] = useState<WatchListItem[]>([]);
  const [isLoading, setLoading] = useState(false);
  const [selected, setSelected] = useState<WatchListItem | null>(null);
  const [isDialogOpen, setDialogOpen] = useState(false);
  const { isAuthenticated, isAuthCheckInProgress, logout } = useAuth();
  const router = useRouter();

  async function fetchWatchList(silent = false) {
    if (!silent) {
      setLoading(true);
    }
    const { data, error } = await getWatchList();
    if (!silent) {
      setLoading(false);
    }
    if (data) {
      setWatchlist(data);
    } else if (error) {
      toast.error(error.message);
    }
  }

  useEffect(() => {
    if (isAuthCheckInProgress) return;
    if (isAuthenticated) {
      fetchWatchList();
    } else {
      router.push("/");
    }
  }, [isAuthenticated]);

  async function removeFromWatchlist(item: WatchListItem) {
    const result = await removeFromWatchList(item.id);
    if (result.error) {
      toast.error("Failed to remove: " + result.error.message);
      return;
    }
    fetchWatchList(true);
    toast.success("Removed from watchlist");
  }

  async function update(item: WatchListItem, notes: string) {
    setDialogOpen(false);
    const { data, error } = await updateWatchListItem(item.id, notes);
    if (data) {
      toast.success("Notes updated");
      fetchWatchList(true);
    } else if (error) {
      toast.error("Failed to update notes: " + error.message);
    }
  }

  return (
    <>
      <div className=" flex w-full justify-between text-left text-7xl font-semibold mb-4 mt-10">
        <div className="ml-10">Your Watchlist</div>

        <div className="flex text-xl mr-10 gap-5">
          <CircleButton
            onClick={() => {
              router.push("/");
            }}
            icon="🏠"
          ></CircleButton>
          <CircleButton
            onClick={() => {
              logout().then(() => {
                router.push("/");
                toast.success("Logged out successfully");
              });
            }}
            icon="➡️"
          ></CircleButton>
        </div>
      </div>

      <div className="flex flex-wrap justify-start content-center gap-4 p-4">
        {isLoading && <CircularProgressIndicator />}

        {!isLoading && watchlist.length === 0 && (
          <p className="text-gray-400 text-center">Your watchlist is empty.</p>
        )}

        {!isLoading &&
          watchlist.length > 0 &&
          watchlist.map((movie) => (
            <MovieCard
              key={movie.id}
              buttonIcon="🖊️"
              showDeleteButton={true}
              onDelete={() => removeFromWatchlist(movie)}
              id={movie.movie.id}
              title={movie.movie.title ?? "Untitled"}
              posterPath={movie.movie.poster_path ?? ""}
              onClick={() => {
                setSelected(movie);
                setDialogOpen(true);
              }}
            />
          ))}

        <InputDialog
          isOpen={isDialogOpen}
          initialValue={selected?.notes ?? ""}
          onClose={() => setDialogOpen(false)}
          onSave={(note) => update(selected!, note)}
        />
      </div>
    </>
  );
}
