import { useEffect, useRef, useState } from "react";
import { useDebouncedCallback } from "use-debounce";

export function SearchBar({ onSearch }: { onSearch: (value: string) => void }) {
  const [query, setQuery] = useState("");
  const firstRender = useRef(true);

  const debouncedSearch = useDebouncedCallback((value) => {
    onSearch(value);
  }, 1000);

  useEffect(() => {
    debouncedSearch(query);
  }, [query]);

  return (
    <input
      value={query}
      onChange={(e) => setQuery(e.target.value)}
      type="text"
      className="w-1/2 align-centre p-2 border rounded-md focus:outline-none focus:ring-0  "
      placeholder="Search Movie"
    />
  );
}
