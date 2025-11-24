export interface Movie {
  id: number;
  title: string;
  overview: string;
  poster_path: string;
  backdrop_Path: string;
  release_date: string;
  vote_average: number;
  vote_count: number;
  popularity: number;
  genreIds: number[];
}
