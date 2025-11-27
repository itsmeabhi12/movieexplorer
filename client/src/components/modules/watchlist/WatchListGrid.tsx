"use client";

import { useRouter } from "next/navigation";
import { useCallback, useEffect, useState } from "react";
import toast from "react-hot-toast";

import getWatchList, {
  removeFromWatchList as apiRemoveFromWatchList,
  updateWatchListItem,
} from "@/src/api/watchlist";
import { useAuth } from "@/src/lib/use-auth";
import { WatchListItem } from "@/src/types/watchlist";

import CircleButton from "../../common/CircleButton";
import { CircularProgressIndicator } from "../../common/CircularProgressIndicator";
import InputDialog from "../../common/InputDialog";
import { MovieCard } from "../../common/MovieCard";

export default function Watchlist() {
  const router = useRouter();
  const { isAuthenticated, isAuthCheckInProgress, logout } = useAuth();

  const [watchlist, setWatchlist] = useState<WatchListItem[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [selectedItem, setSelectedItem] = useState<WatchListItem | null>(null);
  const [isDialogOpen, setIsDialogOpen] = useState(false);

  const fetchWatchList = useCallback(async (silent = false) => {
    if (!silent) setIsLoading(true);
    try {
      const { data, error } = await getWatchList();
      if (error) {
        toast.error(error.message);
        return;
      }
      if (data) {
        setWatchlist(data);
      }
    } finally {
      if (!silent) setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    if (isAuthCheckInProgress) return;

    if (isAuthenticated) {
      void fetchWatchList();
    } else {
      router.push("/");
    }
  }, [isAuthenticated, isAuthCheckInProgress, fetchWatchList, router]);

  const handleRemove = useCallback(
    async (item: WatchListItem) => {
      const result = await apiRemoveFromWatchList(item.id);
      if (result.error) {
        toast.error("Failed to remove: " + result.error.message);
        return;
      }
      toast.success("Removed from watchlist");
      void fetchWatchList(true);
    },
    [fetchWatchList]
  );

  const handleUpdateNotes = useCallback(
    async (item: WatchListItem, notes: string) => {
      setIsDialogOpen(false);
      const { data, error } = await updateWatchListItem(item.id, notes);
      if (error) {
        toast.error("Failed to update notes: " + error.message);
        return;
      }
      if (data) {
        toast.success("Notes updated");
        void fetchWatchList(true);
      }
    },
    [fetchWatchList]
  );

  const handleHomeClick = useCallback(() => {
    router.push("/");
  }, [router]);

  const handleLogoutClick = useCallback(async () => {
    await logout();
    router.push("/");
    toast.success("Logged out successfully");
  }, [logout, router]);

  const handleCardClick = useCallback((item: WatchListItem) => {
    setSelectedItem(item);
    setIsDialogOpen(true);
  }, []);

  const handleDialogClose = useCallback(() => {
    setIsDialogOpen(false);
  }, []);

  const handleDialogSave = useCallback(
    (note: string) => {
      if (!selectedItem) {
        toast.error("No item selected");
        return;
      }
      void handleUpdateNotes(selectedItem, note);
    },
    [handleUpdateNotes, selectedItem]
  );

  return (
    <>
      <div className="flex w-full justify-between text-left text-7xl font-semibold mb-4 mt-10">
        <div className="ml-10">Your Watchlist</div>

        <div className="flex text-xl mr-10 gap-5">
          <CircleButton onClick={handleHomeClick} icon="🏠" />
          <CircleButton onClick={handleLogoutClick} icon="➡️" />
        </div>
      </div>

      <div className="flex flex-wrap justify-start content-center gap-4 p-4">
        {isLoading && <CircularProgressIndicator />}

        {!isLoading && watchlist.length === 0 && (
          <p className="text-gray-400 text-center">Your watchlist is empty.</p>
        )}

        {!isLoading &&
          watchlist.length > 0 &&
          watchlist.map((item) => (
            <MovieCard
              key={item.id}
              buttonIcon="🖊️"
              showDeleteButton={true}
              onDelete={() => handleRemove(item)}
              id={item.movie.id}
              title={item.movie.title ?? "Untitled"}
              posterPath={item.movie.poster_path ?? ""}
              onClick={() => handleCardClick(item)}
            />
          ))}

        <InputDialog
          isOpen={isDialogOpen}
          initialValue={selectedItem?.notes ?? ""}
          onClose={handleDialogClose}
          onSave={handleDialogSave}
        />
      </div>
    </>
  );
}
