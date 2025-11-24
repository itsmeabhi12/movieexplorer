export interface PagedApiResponse<T> {
  results: T[];
  page: number;
  totalPage: number;
  totalResults: number;
}

export interface ApiError {
  message: string;
}
