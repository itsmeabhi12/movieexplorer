import { getMovies } from "../api/movie";
import { ErrorView } from "../components/common/ErrorView";
import { HomeGrid } from "../components/modules/home/HomeGrid";

export default async function Home() {
  const { data, error } = await getMovies();

  return (
    <div>
      {error?.message && <ErrorView message={error.message} />}
      <HomeGrid initialMovies={data?.results ?? []}></HomeGrid>
    </div>
  );
}
