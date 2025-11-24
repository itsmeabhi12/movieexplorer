import WatchListGrid from "@/src/components/modules/watchlist/WatchListGrid";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Watchlist",
  description: "Manage your movie watchlist",
};

export default function Watchlist() {
  return (
    <div>
      <WatchListGrid />
    </div>
  );
}
